package com.example.saneh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class adminClassList extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mClassList;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminclasslist);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mClassList = findViewById(R.id.classList);
        //"classes"
        //Query
        Query query = firebaseFirestore.collection("classesTest");
        //RecyclerOptions

        FirestoreRecyclerOptions<classInfo> options = new FirestoreRecyclerOptions.Builder<classInfo>()
                .setQuery(query, classInfo.class)
                .build();

         adapter= new FirestoreRecyclerAdapter<classInfo, classViewHolder>(options) {
             @NonNull
             @Override
             public classViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classlist, parent, false);
                 return new classViewHolder(view);
             }

             @Override
             protected void onBindViewHolder(@NonNull classViewHolder holder, int position, @NonNull classInfo model) {
                 //model.getCapacity()+""
                holder.class_capacity.setText("Capacity");
                holder.class_interactive.setText("Interactive");
                holder.class_projector.setText("Projector");
             }
         };


         mClassList.setHasFixedSize(true);
         mClassList.setLayoutManager(new LinearLayoutManager(this));
         mClassList.setAdapter(adapter);
    }

    private class classViewHolder extends RecyclerView.ViewHolder{

        private TextView class_capacity;
        private TextView class_interactive;
        private TextView class_projector;

        public classViewHolder(@NonNull View itemView) {
            super(itemView);
            class_capacity = itemView.findViewById(R.id.class_capacity);
            class_interactive = itemView.findViewById(R.id.class_interactive);
            class_projector = itemView.findViewById(R.id.class_projector);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
