package ru.zagorulko.footballscout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class FindFragment extends Fragment  implements RecyclerAdapter.OnItemListener {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.findTeamsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        int[] tempImages = Settings.getTeamImages();
        String[] tempNames = Settings.getTeamNames(Objects.requireNonNull(getContext()));
        int[] images = new int[15];
        String[] names = new String[15];
        int selectedTeam = Settings.getSelectedTeam(getContext());

        for(int i = 0, j = 0; i < tempImages.length; i++) {
            if(i != selectedTeam) {
                images[j] = tempImages[i];
                names[j++] = tempNames[i];
            }
        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(images, names, this);
        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }

    @Override
    public void onItemClick(int position) {
        Toast toast = Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT);
        toast.show();
    }
}
