package ru.zagorulko.footballscout;

// import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.rambler.libs.swipe_layout.SwipeLayout;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        TextView name_title     = this.findViewById(R.id.last_name_title);
        TextView position_title = this.findViewById(R.id.position_title);
        TextView age_title      = this.findViewById(R.id.age_title);
        TextView attack_title   = this.findViewById(R.id.attack_title);
        TextView defender_title = this.findViewById(R.id.defender_title);
        TextView physic_title   = this.findViewById(R.id.physic_title);

        Objects.requireNonNull(getSupportActionBar()).setTitle(Settings.getResearchedTeam(this));

        String[] titles = Settings.getTeamActivityTitle(this);

        name_title.setText(titles[0]);
        position_title.setText(titles[1]);
        age_title.setText(titles[2]);
        attack_title.setText(titles[3]);
        defender_title.setText(titles[4]);
        physic_title.setText(titles[5]);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recycler = findViewById(R.id.recycler);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(new Adapter(this));

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                for(int i= 0; i < recyclerView.getChildCount(); i++) {
                    final Adapter.ViewHolder viewHolder = (Adapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(i);
                    try {
                        assert viewHolder != null;
                        viewHolder.swipeLayout.animateReset();
                    } catch (Exception ignored) {}
                }


            }

        });

    }

    private static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

        private Context context;
        private Player[] players;

        Adapter(Context context) {
            super();
            this.context = context;
            players = getPlayersFromTeam(Settings.getResearchedTeam(context));
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_swipe_players, parent,
                    false);
            final ViewHolder viewHolder = new ViewHolder(itemView);


            // right swipe
            viewHolder.rightView.setClickable(true);
            viewHolder.rightView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    analyze(viewHolder, position);
                }
            });

            // left swipe
            viewHolder.leftView.setClickable(true);
            viewHolder.leftView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRecommend(viewHolder, position);

                }
            });

            viewHolder.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
                @Override
                public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
                }

                // swipe actions
                @Override
                public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {


                    if(moveToRight) {
                        setRecommend(viewHolder, position);
                    } else {
                        analyze(viewHolder, position);
                    }
                }

                @Override
                public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                }

                @Override
                public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                }
            });

            return new ViewHolder(itemView);
        }

        private  void setRecommend(ViewHolder viewHolder, int position) {

            Toast.makeText(context, "Вы порекомедновали " + players[position].getLastName(),
                    Toast.LENGTH_SHORT).show();

            ContentValues contentValues = new ContentValues();
            SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
            contentValues.put("recommended", 1);
            db.update("players", contentValues, "_id=" + players[position].getId(), null);

            viewHolder.swipeLayout.animateReset();
        }

        @SuppressLint("SetTextI18n")
        private void analyze(ViewHolder viewHolder, int position) {

            int energy = Settings.getEnergy(context);
            if(energy > 0) {

                ContentValues contentValues = new ContentValues();
                SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
                contentValues.put("preview", 111);
                db.update("players", contentValues, "_id=" + players[position].getId(), null);
                players = getPlayersFromTeam(Settings.getResearchedTeam(context));


                viewHolder.physical_skill.setText(Integer.toString(players[position].getPhysicalSkill()));
                viewHolder.defender_skill.setText(Integer.toString(players[position].getDefenderSkill()));
                viewHolder.forward_skill.setText(Integer.toString(players[position].getForwardSkill()));

                Settings.setEnergy(context, --energy);
            } else {
                Toast.makeText(context, "У вас не осталось энергии", Toast.LENGTH_SHORT).show();
            }

            viewHolder.swipeLayout.animateReset();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.last_name.setText(players[position].getLastName().split(" ")[1]);
            holder.position.setText(players[position].getPosition());
            holder.age.setText(Integer.toString(players[position].getAge()));
            holder.forward_skill.setText(
                    players[position].getPreview(0) ? Integer.toString(players[position].getForwardSkill()) : "—"
            );
            holder.defender_skill.setText(
                    players[position].getPreview(1) ? Integer.toString(players[position].getDefenderSkill()) : "—"
            );
            holder.physical_skill.setText(
                    players[position].getPreview(2) ? Integer.toString(players[position].getPhysicalSkill()) : "—"
            );

            holder.swipeLayout.setOffset(position);
            holder.swipeLayout.reset();
        }

        @Override
        public int getItemCount() {
            return players.length;
        }



        private Player[] getPlayersFromTeam(String team) {

            SQLiteDatabase db = new DBHelper(context).getReadableDatabase();
            Cursor cursor = db.query(
                    "players",
                    new String[]{"_id", "name", "position", "age", "assaulter_skill", "defender_skill", "physical_skill",
                            "preview"},
                    "team = ?",
                    new String[]{team},
                    null,
                    null,
                    null
            );

            ArrayList<Player> temp = new ArrayList<>();

            try{
                cursor.moveToFirst();
                do{
                    temp.add(new Player(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                            cursor.getInt(6), cursor.getInt(7)));
                }while(cursor.moveToNext());
            } catch (Exception ignored) {
                Toast toast = Toast.makeText(context, "Database is empty. Please, create new game.", Toast.LENGTH_SHORT);
                toast.show();
            }

            Player[] result = new Player[temp.size()];

            temp.toArray(result);
            cursor.close();

            return result;

        }

        private class Player {
            private int id;
            private String last_name;
            private String position;
            private int age;
            private int forward_skill;
            private int defender_skill;
            private int physical_skill;
            private int[] preview;

            Player(int id, String last_name, String position, int age, int forward_skill, int defender_skill, int physical_skill,
                   int preview) {
                this.id = id;
                this.last_name = last_name;
                this.position = position;
                this.age = age;
                this.preview = new int[3];
                // preview
                this.preview[0] = preview / 100;
                this.preview[1] = preview / 10 % 10;
                this.preview[2] = preview % 10;
                // skills
                this.forward_skill = forward_skill;
                this.defender_skill = defender_skill;
                this.physical_skill = physical_skill;
            }

            int getId() {
                return id;
            }

            String getLastName() {
                return last_name;
            }

            String getPosition() {
                return position;
            }

            int getForwardSkill() {
                return forward_skill;
            }

            int getDefenderSkill() {
                return defender_skill;
            }

            int getPhysicalSkill() {
                return physical_skill;
            }

            int getAge() {
                return age;
            }

            boolean getPreview(int skill) {
                /*
                    forward : 0,
                    defender : 1,
                    physical : 2
                 */
                return this.preview[skill] > 0;
            }
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView last_name;
            private final TextView position;
            private final TextView age;
            private final TextView forward_skill;
            private final TextView defender_skill;
            private final TextView physical_skill;
            private final SwipeLayout swipeLayout;
            private final View rightView;
            private final View leftView;

            ViewHolder(View view) {
                super(view);
                last_name      = view.findViewById(R.id.last_name);
                position       = view.findViewById(R.id.position);
                age            = view.findViewById(R.id.age);
                forward_skill  = view.findViewById(R.id.forward_skill);
                defender_skill = view.findViewById(R.id.defender_skill);
                physical_skill = view.findViewById(R.id.physical_skill);
                swipeLayout    = view.findViewById(R.id.swipe_layout);
                rightView      = view.findViewById(R.id.right_view);
                leftView       = view.findViewById(R.id.left_view);

            }
        }
    }
}
