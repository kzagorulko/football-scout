package ru.zagorulko.footballscout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;

import ru.rambler.libs.swipe_layout.SwipeLayout;


public class HomeFragment extends Fragment {


    private void makeNextOffSeason() {

        SQLiteDatabase db = new DBHelper(getContext()).getWritableDatabase();

        Cursor cursor = db.query(
                DBHelper.TABLE_PLAYERS,
                new String[]{"_id"},
                DBHelper.KEY_RECOMMENDED + " = ?",
                new String[]{"1"},
                null,
                null,
                DBHelper.KEY_VIEW + " DESC, " + DBHelper.KEY_POTENTIAL + " DESC",
                "4");
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TEAM,
                Settings.getTeamNames(Objects.requireNonNull(
                        getContext()
                ))[Settings.getSelectedTeam(getContext())]);
        contentValues.put(DBHelper.KEY_BY_PLAYER, 1);
        contentValues.put(DBHelper.KEY_RECOMMENDED, 0);
        try {
            cursor.moveToFirst();
            do{
                db.update(DBHelper.TABLE_PLAYERS, contentValues, "_id=" +
                        cursor.getInt(0), null);
            }while(cursor.moveToNext());
        } catch (Exception ignore) {}

        cursor.close();
        contentValues.clear();

        cursor = db.query(
                DBHelper.TABLE_PLAYERS,
                new String[]{
                        DBHelper.KEY_ID, DBHelper.KEY_AGE, DBHelper.KEY_POTENTIAL, DBHelper.KEY_PHYSICAL_SKILL,
                        DBHelper.KEY_ASSAULTER_SKILL, DBHelper.KEY_DEFENDER_SKILL
                },
                null,
                null,
                null,
                null,
                null
        );

        try {
            cursor.moveToFirst();
            do {

                int characteristic = (int)(Math.random() * 3 + 1);
                int growth = (int)(Math.random() * 3 + 1);

                contentValues.put(DBHelper.KEY_GROWTH, growth = cursor.getInt(1) < 33 ? growth : -growth);

                if (growth + cursor.getInt(4) + cursor.getInt(5) + cursor.getInt(3) >
                        cursor.getInt(2)) {
                    growth = 0;
                    //TODO this check must be otherwiseю
                }

                switch (characteristic) {
                    case 1:
                        contentValues.put(DBHelper.KEY_ASSAULTER_SKILL, cursor.getInt(4) + growth); break;
                    case 2:
                        contentValues.put(DBHelper.KEY_DEFENDER_SKILL, cursor.getInt(5) + growth); break;
                    case 3:
                        contentValues.put(DBHelper.KEY_PHYSICAL_SKILL, cursor.getInt(3) + growth); break;
                }
                contentValues.put(DBHelper.KEY_AGE, cursor.getInt(1) + 1);

                db.update(
                        DBHelper.TABLE_PLAYERS,
                        contentValues,
                        "_id=" + cursor.getInt(0),
                        null
                );

            }while(cursor.moveToNext());
        } catch (Exception ignore) {}

        cursor.close();

        DataGeneration.generate(getContext(), 10, false);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView progressTitle = view.findViewById(R.id.progress_title);
        TextView energy = view.findViewById(R.id.progress_energy);
        Button nextOffSeason = view.findViewById(R.id.button_next);

        progressTitle.setText(Settings.getProgressTitle(Objects.requireNonNull(getContext())));
        energy.setText(Settings.getEnergyTitle(getContext()));
        nextOffSeason.setText(Settings.getProgressButtonTitle(getContext()));

        nextOffSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO rewrite this pos.
                Objects.requireNonNull(getActivity()).setContentView(R.layout.activity_data_generation);

                makeNextOffSeason();
                Settings.setEnergy(Objects.requireNonNull(getContext()), 20);

                // TODO and this
                Intent intent = new Intent(getContext(), GameActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // content building
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        RecyclerView recycler = view.findViewById(R.id.recycler);

        view.setBackgroundColor(0xFFFFFFFF);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(new Adapter(getContext()));

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                for(int i= 0; i < recyclerView.getChildCount(); i++) {
                    final Adapter.ViewHolder viewHolder = (Adapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(i);
                    try {
                        assert viewHolder != null;
                        viewHolder.swipe_layout.animateReset();
                    } catch (Exception ignored) {}
                }


            }

        });


        // Inflate the layout for this fragment
        return view;
    }

    private static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context context;
        private Player[] players;

        Adapter(Context context){
            super();
            this.context = context;
            this.players = getPlayers();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_progress, parent,
                    false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            if(players.length > 0) {
                holder.last_name.setText(players[position].last_name);

                int image;

                holder.progress_image.setVisibility(View.VISIBLE);
                switch (players[position].progress) {
                    case 3:
                        image = R.drawable.super_progress;
                        break;
                    case 2:
                        image = R.drawable.very_good;
                        break;
                    case 1:
                        image = R.drawable.good;
                        break;
                    case -1:
                        image = R.drawable.bad;
                        break;
                    case -2:
                        image = R.drawable.very_bad;
                        break;
                    case -3:
                        image = R.drawable.terrible;
                        break;
                    default:
                        image = R.drawable.noprogress;
                        break;

                }

                holder.progress_image.setImageResource(image);

                holder.swipe_layout.setOffset(position);
                holder.swipe_layout.reset();
            } else {
                holder.last_name.setText("Вы ещё не нашли ни одного игрока");
                holder.progress_image.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return players.length > 0 ? players.length : 1;
        }

        private Player[] getPlayers(){
            SQLiteDatabase db = new DBHelper(context).getReadableDatabase();

            ArrayList<Player> temp = new ArrayList<>();

            Cursor cursor = db.query(
                    DBHelper.TABLE_PLAYERS,
                    new String[]{DBHelper.KEY_NAME, DBHelper.KEY_GROWTH},
                    "team = ? and found_by_player = ?",
                    new String[]{
                            Settings.getTeamNames(Objects.requireNonNull(context))[Settings.getSelectedTeam(context)], "1"
                    },
                    null, null, null
            );

            try {
                cursor.moveToFirst();
                do{
                    temp.add(new Player(cursor.getString(0), cursor.getInt(1)));
                }while(cursor.moveToNext());
            }catch (Exception ignored) {};

            Player[] result = new Player[temp.size()];

            temp.toArray(result);
            cursor.close();

            return result;


        }

        class Player {
            private final String last_name;
            private final int progress;

            Player(String last_name, int progress) {
                this.last_name = last_name;
                this.progress = progress;
            }
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView last_name;
            private final ImageView progress_image;
            private final SwipeLayout swipe_layout;

            ViewHolder(View view){
                super(view);
                last_name = view.findViewById(R.id.player_progress_name);
                progress_image = view.findViewById(R.id.progress_image);
                swipe_layout = view.findViewById(R.id.players_progress_layout);
            }
        }
    }

}
