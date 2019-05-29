package ru.zagorulko.footballscout;

// import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                    } catch (Exception ignored) {};
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_swipe_players, parent,
                    false);
            final ViewHolder viewHolder = new ViewHolder(itemView);

            View.OnClickListener onClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.swipeLayout.animateReset();
                }
            };

            // right swipe
            viewHolder.rightView.setClickable(true);
            viewHolder.rightView.setOnClickListener(onClick);

            // left swipe
            viewHolder.leftView.setClickable(true);
            viewHolder.leftView.setOnClickListener(onClick);

            viewHolder.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
                @Override
                public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
                }

                // swipe actions
                @Override
                public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
                    Toast.makeText(swipeLayout.getContext(),
                            (moveToRight ? "Left" : "Right") + " clamp reached",
                            Toast.LENGTH_SHORT)
                            .show();


                    viewHolder.swipeLayout.animateReset();
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

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.last_name.setText(players[position].getLastName().split(" ")[1]);
            holder.position.setText(players[position].getPosition());
            holder.age.setText(Integer.toString(players[position].getAge()));
            holder.forward_skill.setText(Integer.toString(players[position].getForwardSkill()));
            holder.defender_skill.setText(Integer.toString(players[position].getDefenderSkill()));
            holder.physical_skill.setText(Integer.toString(players[position].getPhysicalSkill()));

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
                    new String[]{"name", "position", "age", "defender_skill", "assaulter_skill", "physical_skill"},
                    "team = ?",
                    new String[]{Settings.getResearchedTeam(context)},
                    null,
                    null,
                    null
            );

            ArrayList<Player> temp = new ArrayList<>();

            try{
                cursor.moveToFirst();
                do{
                    temp.add(new Player(cursor.getString(0), cursor.getString(1),
                            cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                            cursor.getInt(5)));
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
            private String last_name;
            private String position;
            private int age;
            private int forward_skill;
            private int defender_skill;
            private int physical_skill;

            Player(String last_name, String position, int age, int forward_skill, int defender_skill, int physical_skill) {
                this.last_name = last_name;
                this.position = position;
                this.age = age;
                this.forward_skill = forward_skill;
                this.defender_skill = defender_skill;
                this.physical_skill = physical_skill;
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

            public int getAge() {
                return age;
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
