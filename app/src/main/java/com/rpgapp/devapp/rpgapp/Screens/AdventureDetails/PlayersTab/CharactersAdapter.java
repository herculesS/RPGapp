package com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.PlayersTab;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rpgapp.devapp.rpgapp.Model.Character;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.R;

import java.util.ArrayList;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    private ArrayList<Character> mCharacters;

    public CharactersAdapter(ArrayList<Character> characters) {
        mCharacters = characters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.characters_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mCharacterName.setText(mCharacters.get(position).getName());
        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
        usersCollection
                .document(mCharacters.get(position).getUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document != null) {
                                User user = document.toObject(User.class);
                                holder.mPlayerName.setText(user.getName());
                                notifyDataSetChanged();
                            }
                        } else {
                            holder.mPlayerName.setText("NO NAME!");
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mCharacters != null ? mCharacters.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCharacterName;
        public TextView mPlayerName;

        public ViewHolder(View itemView) {
            super(itemView);
            mCharacterName = itemView.findViewById(R.id.text_view_character_name);
            mPlayerName = itemView.findViewById(R.id.text_view_player_name);
        }
    }
}
