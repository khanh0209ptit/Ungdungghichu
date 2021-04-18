package com.example.a131_sqlitetodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBase dataBase;
    ListView lvCV;
    ArrayList<CongViec> congViecArrayList;
    CongViecAdapter adapter;
    EditText edtTen;
    Button btnThem,btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCV = findViewById(R.id.listviewCongViec);
        congViecArrayList = new ArrayList<>();

        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec,congViecArrayList);
        lvCV.setAdapter(adapter);

        //khoi tao database ghi chu
        dataBase = new DataBase(this,"ghichu.sqlite",null,1);

        //tao bang Cong viec
        // id -> kieu -> khoa chinh -> tang dan
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

        //insert data
        //id tang dan nen de null cho du tham so
//        dataBase.QueryData("INSERT INTO CongViec VALUES(null, 'Ung dung ghi chu')");

        GetDataCV();


    }
    public void DialogSuaCV(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);

        EditText edtTenCV = dialog.findViewById(R.id.edtEditCongViec);
        Button btnXacnhanthemCV = dialog.findViewById(R.id.btnCapnhap);
        Button btnHuyCapnhapCV = dialog.findViewById(R.id.btnHuyCApNhap);

        edtTenCV.setText(ten);

        btnXacnhanthemCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCapnhap = edtTenCV.getText().toString().trim();
                //cap nhap

                dataBase.QueryData("UPDATE CongViec SET TenCV = '"+ tenCapnhap +"' WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Da cap nhap", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCV();
            }
        });

        btnHuyCapnhapCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void GetDataCV(){
        //select data
        Cursor dataCV = dataBase.GetData("SELECT * FROM CongViec");
        //xoa mang k se bi lap du lieu
        congViecArrayList.clear();
        while (dataCV.moveToNext()){
            //id so 0 , ten so 1
            String ten = dataCV.getString(1);

            int id = dataCV.getInt(0);
            congViecArrayList.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }

    //add menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //click vao
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }

    public void DialogXoa(String tenCV, int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Ban co chac chan muon xoa CV" + tenCV + "khong");

        dialogXoa.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataBase.QueryData("DELETE FROM CongViec WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Da xoa xong" + tenCV, Toast.LENGTH_SHORT).show();
                GetDataCV();
            }
        });
        dialogXoa.setNegativeButton("khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogXoa.show();
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_congviec);
        dialog.show();

         edtTen = dialog.findViewById(R.id.edtthemCV);
         btnThem = dialog.findViewById(R.id.btnThem);
         btnHuy = dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCVThem = edtTen.getText().toString();
                if (tenCVThem.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap ten CV can them", Toast.LENGTH_SHORT).show();
                }else {
                    dataBase.QueryData("INSERT INTO CongViec VALUES(null, '"+ tenCVThem +"')");
                    Toast.makeText(MainActivity.this, "Hoan thanh", Toast.LENGTH_SHORT).show();
//                    khi them x se tu dong dismiss
                    dialog.dismiss();
                    GetDataCV();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}