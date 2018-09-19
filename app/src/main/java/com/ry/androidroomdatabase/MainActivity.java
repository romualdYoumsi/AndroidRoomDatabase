package com.ry.androidroomdatabase;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ry.androidroomdatabase.database.UserRepository;
import com.ry.androidroomdatabase.local.UserDataSource;
import com.ry.androidroomdatabase.local.UserDatabase;
import com.ry.androidroomdatabase.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ListView listUsers;
    private FloatingActionButton fab;

//    Adapter
    List<User> userList = new ArrayList<>();
    ArrayAdapter adapter;

//    Database
    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;

    private void loadData() {
//        use RxJava
        Disposable disposable = userRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        onGetAlluserSucess(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAlluserSucess(List<User> users) {
        userList.clear();
        userList.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private void deleteAllUsers() {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                userRepository.deleteAllUser();
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
//                                refresh the data
                        loadData();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void updateUser(final User user) {

        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                userRepository.updateUser(user);
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
//                                refresh the data
                        loadData();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void deleteUser(final User user) {

        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                userRepository.deleteUser(user);
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
//                                refresh the data
                        loadData();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        init
        compositeDisposable = new CompositeDisposable();

//        init view
        listUsers = (ListView) findViewById(R.id.list_users);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, userList);
        registerForContextMenu(listUsers);
        listUsers.setAdapter(adapter);

//        Database
        UserDatabase userDatabase = UserDatabase.getInstance(MainActivity.this);
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));

//        Load data from Database
        loadData();

//        Events
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Random email
                Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        User user = new User("Romuald Youmsi", UUID.randomUUID().toString()+"@gmail.com");
                        userRepository.insertUser(user);
                        e.onComplete();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Toast.makeText(MainActivity.this, "User add", Toast.LENGTH_LONG).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
//                                refresh the data
                                loadData();
                            }
                        });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                deleteAllUsers();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Select action");

        menu.add(Menu.NONE, 0, Menu.NONE, "UPDATE");
        menu.add(Menu.NONE, 1, Menu.NONE, "DELETE");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final User user = userList.get(info.position);
        switch (item.getItemId()) {
            //update
            case 0:  {

                final EditText etName = new EditText(MainActivity.this);
                etName.setText(user.getName());
                etName.setHint("Enter your name");

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Edit")
                        .setMessage("Edit your name")
                        .setView(etName)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (TextUtils.isEmpty(etName.getText().toString())) {
                                    return;
                                }
                                else {
                                    user.setName(etName.getText().toString());
                                    updateUser(user);
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                }
                break;
            //delete
            case 1:  {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Do you want to delete "+user.toString())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteUser(user);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                }
                break;

        }
        return true;
    }
}
