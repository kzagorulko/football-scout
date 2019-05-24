package ru.zagorulko.footballscout;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
// import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class TaskFragment extends Fragment {


    @SuppressLint("Recycle")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        // Select 3 position from database
        SQLiteDatabase db = new DBHelper(getContext()).getReadableDatabase();

        Cursor cursor = db.query("players", new String[]{"position"}, "team = ?", new String[]{
                         Settings.getTeamNames(Objects.requireNonNull(getContext()))[
                                 Settings.getSelectedTeam(Objects.requireNonNull(getContext()))
                        ]
                }, null, null, "potential", "3");

        String[] positions = new String[3];
        int i = 0;
        try {
            cursor.moveToFirst();
            do {
                positions[i++] = cursor.getString(0);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Toast toast = Toast.makeText(getContext(), "Database is empty. Please, create new " + "game.",
                    Toast.LENGTH_SHORT);
            toast.show();
            positions = new String[]{"error", "error", "error"};
        }


        cursor.close();
        db.close();

        // Color invert
        float [] NEGATIVE = {
                -1.0f,  0,     0,    0, 255, // red
                0,  -1.0f,     0,    0, 255, // green
                0,      0, -1.0f,    0, 255, // blue
                0,      0,     0, 1.0f,   0,  // alpha
        };

        // "ЦЗ", "ЛЗ", "ПЗ", "ЦП", "АПЛ", "АПЦ", "АПП", "НП" / "LB", "CB", "RB", "CM", "LW", "AM", "RW", "CF"
        for(String position : positions) {
            ImageView imageView, imageView1;
            imageView = imageView1 = null;
            switch (position) {
                case "ЦЗ":
                case "CB": imageView = view.findViewById(R.id.imageDefenderCenterLeft);
                           imageView1 = view.findViewById(R.id.imageDefenderCenterRight); break;

                case "ЛЗ":
                case "LB": imageView = view.findViewById(R.id.imageDefenderLeft); break;

                case "ПЗ":
                case "RB": imageView = view.findViewById(R.id.imageDefenderRight); break;

                case "ЦП":
                case "CM": imageView = view.findViewById(R.id.imageMidfieldCenterLeft);
                           imageView1 = view.findViewById(R.id.imageMidfielderCenterRight); break;

                case "АПЛ":
                case "LW": imageView = view.findViewById(R.id.imageAttackingMidfielderLeft); break;

                case "АПЦ":
                case "AM": imageView = view.findViewById(R.id.imageAttackingMidfielderCenter); break;

                case "АПП":
                case "RW": imageView = view.findViewById(R.id.imageAttackingMidfielderRight); break;

                case "НП":
                case "CF": imageView = view.findViewById(R.id.imageForward); break;
            }

            if (imageView  != null) imageView.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
            if (imageView1 != null) imageView1.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
        }

        return view;
    }

}
