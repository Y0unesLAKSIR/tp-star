package com.example.tp3.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp3.adapter.StarAdapter;
import com.example.tp3.beans.Star;
import com.example.tp3.service.StarService;
import java.util.ArrayList;
import java.util.List;

import com.example.tp3.R;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StarAdapter starAdapter;
    private List<Star> stars;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        service = StarService.getInstance();
        init();
        stars = service.findAll();
        
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, stars);
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Use LinearLayoutManager, not LinearLayoutCompat
    }
    
    public void init() {
        service.create(new Star("Leonardo DiCaprio", "https://m.media-amazon.com/images/M/MV5BMjI0MTg3MzI0M15BMl5BanBnXkFtZTcwMzQyODU2Mw@@._V1_UY317_CR10,0,214,317_AL_.jpg", 3.5f));
        service.create(new Star("Scarlett Johansson", "https://m.media-amazon.com/images/M/MV5BMTM3OTUwMDYwNl5BMl5BanBnXkFtZTcwNTUyNzc3Nw@@._V1_UY317_CR23,0,214,317_AL_.jpg", 4.8f));
        service.create(new Star("Dwayne Johnson", "https://m.media-amazon.com/images/M/MV5BMTkyNDQ3NzAxM15BMl5BanBnXkFtZTgwODIwMTQ0NTE@._V1_UX214_CR0,0,214,317_AL_.jpg", 2.2f));
        service.create(new Star("Emma Stone", "https://m.media-amazon.com/images/M/MV5BMjI4NjM1NDkyN15BMl5BanBnXkFtZTgwODgyNTY1MjE@._V1_UX214_CR0,0,214,317_AL_.jpg", 4.6f));
        service.create(new Star("Tom Hanks", "https://m.media-amazon.com/images/M/MV5BMTQ2MjMwNDA3Nl5BMl5BanBnXkFtZTcwMTA2NDY3NQ@@._V1_UY317_CR2,0,214,317_AL_.jpg", 5.0f));
        service.create(new Star("Jennifer Lawrence", "https://m.media-amazon.com/images/M/MV5BOTU3NDE5MDQ4MV5BMl5BanBnXkFtZTgwMzE5ODQ0MDI@._V1_UX214_CR0,0,214,317_AL_.jpg", 1.3f));
        service.create(new Star("Robert Downey Jr.", "https://m.media-amazon.com/images/M/MV5BNzg1MTUyNDYxOF5BMl5BanBnXkFtZTgwNTQ4MTE2MjE@._V1_UX214_CR0,0,214,317_AL_.jpg", 3.7f));
        service.create(new Star("Meryl Streep", "https://m.media-amazon.com/images/M/MV5BMTU4Mjk5MDExOF5BMl5BanBnXkFtZTcwOTU1MTMyMw@@._V1_UY317_CR6,0,214,317_AL_.jpg", 5.0f));
    }
}