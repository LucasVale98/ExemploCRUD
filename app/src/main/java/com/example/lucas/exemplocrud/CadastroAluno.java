package com.example.lucas.exemplocrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucas.exemplocrud.model.Aluno;

public class CadastroAluno extends AppCompatActivity implements View.OnClickListener {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private AlunoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        nome = findViewById(R.id.edit_nome);
        cpf = findViewById(R.id.edit_cpf);
        telefone = findViewById(R.id.edit_telefone);

        dao = new AlunoDAO(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.salvar){
            Aluno aluno = new Aluno();

            aluno.setNome(nome.getText().toString());
            aluno.setCpf(nome.getText().toString());
            aluno.setTelefone(nome.getText().toString());

            long id = dao.inserir(aluno);
            Toast.makeText(this, "Aluno inserido com o ID " + id, Toast.LENGTH_SHORT);
        }
    }
}
