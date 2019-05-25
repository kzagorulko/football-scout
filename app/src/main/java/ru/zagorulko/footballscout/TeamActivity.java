package ru.zagorulko.footballscout;

// import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recycler = findViewById(R.id.recycler);

        recycler.setLayoutManager(manager);
        recycler.setAdapter(new Adapter());

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                for(int i= 0; i < recyclerView.getChildCount(); i++) {
                    final Adapter.ViewHolder viewHolder = (Adapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(i);
                    try {
                        assert viewHolder != null;
                        viewHolder.swipeLayout.animateReset();
                    }catch (Exception ignored) {};
                }


            }

        });

    }

    private static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView last_name;
            private final TextView position;
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
