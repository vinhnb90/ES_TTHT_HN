package esolutions.com.esdatabaselib.example.activity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import esolutions.com.esdatabaselib.R;
import esolutions.com.esdatabaselib.baseSqlite.LazyList;
import esolutions.com.esdatabaselib.baseSqlite.SqlDAO;
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;
import esolutions.com.esdatabaselib.example.source.sqliteConfig.ClassRoom;
import esolutions.com.esdatabaselib.example.source.sqliteConfig.ESDbConfig;
import esolutions.com.esdatabaselib.example.source.sqliteConfig.Student;

import static android.util.Log.d;

public class DatabaseActivity extends AppCompatActivity {
    Button btnCreateTable, btnInsertData10k, btnReloadData;
    TextView tvPath;
    ListView lvData;

    Thread threadCreateDB, theadInsertData10k, threadReloadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (btnCreateTable = (Button) findViewById(R.id.btnCreateTable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (threadCreateDB != null && threadCreateDB.isAlive()) {
                    Toast.makeText(DatabaseActivity.this, "Đang trong quá trình tạo db, thử lại sau!", Toast.LENGTH_SHORT).show();
                    return;
                }

                threadCreateDB = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SqlHelper.setupDB(DatabaseActivity.this, ESDbConfig.class, new Class[]{
                                    ClassRoom.class, Student.class});
                            //try reload because lib reload is not working
                            DatabaseActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        (tvPath = (TextView) findViewById(R.id.tvPathFileDB)).setText(SqlHelper.getDatabasePath());
                                    } catch (Exception e) {
                                        Toast.makeText(DatabaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (final Exception e) {
                            DatabaseActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DatabaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            e.printStackTrace();
                        }
                    }
                });
                threadCreateDB.start();
            }
        });

        (btnInsertData10k = (Button) findViewById(R.id.btnInsertData10k)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theadInsertData10k != null && theadInsertData10k.isAlive()) {
                    Toast.makeText(DatabaseActivity.this, "Đang trong quá trình insert dữ liệu, thử lại sau!", Toast.LENGTH_SHORT).show();
                    return;
                }

                theadInsertData10k = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SqlDAO sqlDAO = new SqlDAO(SqlHelper.getIntance().openDB(), DatabaseActivity.this);
                            sqlDAO.deleteAll(Student.class);

                            //create list 10k student
                            List<Student> listDataDump = new ArrayList<Student>();
                            for (int i = 0; i < 10000; i++) {
                                Student aStudent = new Student();
                                aStudent.setmName("Học sinh thứ " + i + 1);
                                String className = (i + 1) % 10000 + "";
                                aStudent.setClassName("Lớp " + className);
                                listDataDump.add(aStudent);
                            }

                            Long[] indexs = sqlDAO.insert(Student.class, listDataDump);

                            DatabaseActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        fillListView();
                                    } catch (Exception e) {
                                        Toast.makeText(DatabaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (final Exception e) {
                            DatabaseActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DatabaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
                theadInsertData10k.start();
            }
        });

        (btnReloadData=(Button)findViewById(R.id.btnReloadData)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (threadReloadData != null && threadReloadData.isAlive()) {
                    Toast.makeText(DatabaseActivity.this, "Đang trong quá trình reload dữ liệu hiển thị, thử lại sau!", Toast.LENGTH_SHORT).show();
                    return;
                }

                threadReloadData = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    fillListView();
                                } catch (Exception e) {
                                    Toast.makeText(DatabaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

                threadReloadData.start();
            }
        });
    }

    private void fillListView() throws Exception {
        SqlDAO sqlDAO = new SqlDAO(SqlHelper.getIntance().openDB(), DatabaseActivity.this);
        LazyList<Student> students = sqlDAO.selectAllLazy(Student.class, null);

        List<String> listData = new ArrayList<>();
        for(int i = 0; i<students.size() ;i++)
        {
//        for (Student student : students) {
//            listData.add(student != null ? student.getmName() + student.getClassName() : null);

            listData.add(students.get(i).getmName() + " " + students.get(i).getClassName());
        }

        (lvData = (ListView) findViewById(R.id.lvdata)).
                setAdapter(new ArrayAdapter<String>(DatabaseActivity.this, android.R.layout.simple_list_item_1, listData));

    }
}
