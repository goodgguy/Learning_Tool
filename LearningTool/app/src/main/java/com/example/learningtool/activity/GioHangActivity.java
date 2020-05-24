package com.example.learningtool.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learningtool.R;
import com.example.learningtool.adapter.GioHangAdapter;
import com.example.learningtool.model.GioHang;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ListView lvGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolBar();
        CheckData();
        EvenUltil();
        CatchOnItemListView();
    }

    private void CatchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa Sản Phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.gioHangArrayList.size()<=0)
                        {
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else
                        {
                            MainActivity.gioHangArrayList.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if(MainActivity.gioHangArrayList.size()<=0)
                            {
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else
                            {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUltil() {
        double tongtien=0;
        for (int i=0;i<MainActivity.gioHangArrayList.size();i++)
        {
            tongtien+=MainActivity.gioHangArrayList.get(i).getPrice();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        txtTongTien.setText(decimalFormat.format(tongtien)+"Đ");
    }

    private void CheckData() {
        if(MainActivity.gioHangArrayList.size()<=0)
        {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else
        {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGioHang=findViewById(R.id.listviewGioHang);
        txtThongBao=findViewById(R.id.txtThongBao);
        txtTongTien=findViewById(R.id.textviewTongTien);
        btnThanhToan=findViewById(R.id.btnThanhToanGioHang);
        btnTiepTucMua=findViewById(R.id.btnTiepTucMuaHang);
        toolbarGioHang=findViewById(R.id.toolbarGioHang);
        gioHangAdapter=new GioHangAdapter(GioHangActivity.this,MainActivity.gioHangArrayList);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
