package com.example.lucas.exemplocrud;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.lucas.exemplocrud.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        listView = findViewById(R.id.listar_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,
                android.R.layout.simple_list_item_1, alunosFiltrados);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_serch)
                .getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraAluno(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraAluno(String nome){
        alunosFiltrados.clear();
        for (Aluno aluno : alunos){
            if (aluno.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(aluno);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem menuItem){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        final Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente desejar excluir o aluno?")
                .setNegativeButton("NÃU", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alunosFiltrados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        dao.excluir(alunoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_cadastrar){
            Intent intent = new Intent(this, CadastroAluno.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        listView.invalidateViews();
    }

}
