package com.latihan.pertemuan4;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.latihan.pertemuan4.Model.PostPutDelKontak;
import com.latihan.pertemuan4.Rest.ApiClient;
import com.latihan.pertemuan4.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EditActivity extends AppCompatActivity {
    EditText edtId, edtNama, edtNomor;
    Button btUpdate, btDelete, btBack;
    ApiInterface mApiInterface;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edtId = (EditText) findViewById(R.id.edtId);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtNomor = (EditText) findViewById(R.id.edtNomor);
        Intent mIntent = getIntent();
        edtId.setText(mIntent.getStringExtra("Id"));
        edtId.setTag(edtId.getKeyListener());
        edtId.setKeyListener(null);
        edtNama.setText(mIntent.getStringExtra("Nama"));
        edtNomor.setText(mIntent.getStringExtra("Nomor"));
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        btUpdate = (Button) findViewById(R.id.btUpdate2);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PostPutDelKontak> updateKontakCall = mApiInterface.putKontak(
                        edtId.getText().toString(),
                        edtNama.getText().toString(),
                        edtNomor.getText().toString());
                updateKontakCall.enqueue(new Callback<PostPutDelKontak>() {
                    @Override
                    public void onResponse(Call<PostPutDelKontak> call,
                                           Response<PostPutDelKontak> response) {
                        MainActivity.ma.refresh();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<PostPutDelKontak> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Periksa kembali koneksi anda",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btDelete = (Button) findViewById(R.id.btDelete2);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtId.getText().toString().trim().isEmpty()==false){
                    Call<PostPutDelKontak> deleteKontak =
                            mApiInterface.deleteKontak(edtId.getText().toString());
                    deleteKontak.enqueue(new Callback<PostPutDelKontak>() {
                        @Override
                        public void onResponse(Call<PostPutDelKontak> call,
                                               Response<PostPutDelKontak> response) {
                            MainActivity.ma.refresh();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<PostPutDelKontak> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Periksa kombali koneksi anda",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Periksa kembali koneksi anda",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        btBack = (Button) findViewById(R.id.btBackGo);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ma.refresh();
                finish();
            }
        });
    }
}