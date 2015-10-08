package framgia.employeemanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 06/10/2015.
 */
public class DisplayEmployeeDetailActivity extends Activity {
    private static final int VIEWMODE = 1;
    private static final int ADDMODE = 2;
    private static final int EDITMODE = 3;
    private static final int SELECT_PICTURE = 1;
    private static EditText txJoinDate = null;
    private static EditText txBirthDay = null;
    private static EditText txPhone = null;
    private static EditText txLeaveDate = null;
    private static EditText txname = null;
    private static Spinner spinnerPlaceOfBirth = null;
    private static Spinner spinnerPosition = null;
    private static Spinner spinnerDepartment = null;
    private static Spinner spinnerStatus = null;
    private static Button btupdate = null;
    private static Button btCancel = null;
    private static Button btEdit = null;
    private static Button btAdd = null;
    private static Button btCancelAdd = null;
    private static ImageView avartar = null;
    private static ArrayAdapter<CharSequence> adapterSpinerPlaceOfBirth;
    private static ArrayAdapter<CharSequence> adapterSpinerDepartment;
    private static ArrayAdapter<CharSequence> adapterSpinerPosition;
    private static ArrayAdapter<CharSequence> adapterSpinerStatus;
    private Uri selectedImageUri;
    private static Employee employee;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_employee);
        employee = new Employee();
        //Set id to items
        setFindViewById();
        //receive intent to set mode
        Intent intent = getIntent();
        int displayMode = intent.getIntExtra("DisplayMode", VIEWMODE);
        setModeDisplay(displayMode);
        //set listener for buttons
        setButtonListenner();
    }

    public void setFindViewById() {
        txname = (EditText) findViewById(R.id.editName);
        txJoinDate = (EditText) findViewById(R.id.editJoinDate);
        txLeaveDate = (EditText) findViewById(R.id.editLeaveDate);
        txBirthDay = (EditText) findViewById(R.id.editBirthday);
        //Place of birth
        spinnerPlaceOfBirth = (Spinner) findViewById(R.id.spinnerPlaceOfBirth);
        adapterSpinerPlaceOfBirth = ArrayAdapter.createFromResource(this,
                R.array.place_of_birth_array, android.R.layout.simple_spinner_item);
        adapterSpinerPlaceOfBirth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaceOfBirth.setAdapter(adapterSpinerPlaceOfBirth);
        //Position
        spinnerPosition = (Spinner) findViewById(R.id.spinnerPosition);
        adapterSpinerPosition = ArrayAdapter.createFromResource(this,
                R.array.position_array, android.R.layout.simple_spinner_item);
        adapterSpinerPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosition.setAdapter(adapterSpinerPosition);
        //Department
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDeparment);
        adapterSpinerDepartment = ArrayAdapter.createFromResource(this,
                R.array.deparmentListview_array, android.R.layout.simple_spinner_item);
        adapterSpinerDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterSpinerDepartment);
        //Status
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        adapterSpinerStatus = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapterSpinerStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterSpinerStatus);
        //Phone
        txPhone = (EditText) findViewById(R.id.editPhone);
        btupdate = (Button) findViewById(R.id.btUpdate);
        btCancel = (Button) findViewById(R.id.buttonCancelUpdate);
        btEdit = (Button) findViewById(R.id.buttonEdit);
        btAdd = (Button) findViewById(R.id.buttonAdd);
        btCancelAdd = (Button) findViewById(R.id.buttonCancelAdd);
        avartar = (ImageView) findViewById(R.id.imageAvatar);
        avartar.setImageResource(R.drawable.avar);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                avartar.setImageURI(selectedImageUri);
            }
        }
    }
    // Save file to folder
    private void saveFile(Uri sourceuri, File destination) {
        try {
            File source = new File(sourceuri.getPath());
            FileChannel src = new FileInputStream(source).getChannel();
            FileChannel dst = new FileOutputStream(destination).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    /******
     * Function to set display mode
     *************/
    public void setModeDisplay(int mode) {
        switch (mode) {
            case VIEWMODE:
                getEmployee();
                displayEmployeeDetail();
                setButton(VIEWMODE);
                break;
            case ADDMODE:
                enableEditEmployee();
                setButton(ADDMODE);
                break;
            case EDITMODE:
                getEmployee();
                displayEmployeeDetail();
                enableEditEmployee();
                setButton(EDITMODE);
                break;
            default:
                break;//do nothing
        }
    }

    /******
     * Function to display
     *************/
    public void displayEmployeeDetail() {
        //Set name
        txname.setText(employee.getName());
        txname.setEnabled(false);
        //Set Place of birth
        setPlaceOfBirthSpinner(employee.getAddress());
        //Set birthday
        txBirthDay.setText(employee.getBirthday());
        txBirthDay.setEnabled(false);
        //Set Phone
        txPhone.setText(employee.getPhone());
        txPhone.setEnabled(false);
        //SetPosition
        setPositionSpinner(employee.getPosition());
        //SetDepartment
        setDepartmentSpinner(employee.getDepartment());
        //SetStatus
        setStatusSpinner(employee.getStatus());
        //SetJoinDate
        txJoinDate.setText(employee.getJoinDate());
        txJoinDate.setEnabled(false);
        //SetLeaveDate
        txLeaveDate.setText(employee.getLeaveDate());
        txLeaveDate.setEnabled(false);
        //Set avatar
        avartar.setImageResource(R.drawable.avar);
        avartar.setEnabled(false);
    }

    //Get an Employee
    public void getEmployee() {
        //create an annonymos employee
        employee.setName("Emplpyee");
        employee.setPosition("Manager");
        employee.setBirthday("04/07/1989");
        employee.setLeaveDate("");
        employee.setJoinDate("15/09/2015");
        employee.setAddress("Đà Nẵng");
        employee.setDepartment("Education");
        employee.setPhone("0905477041");
        employee.setImage("avar");
        employee.setStatus("Parttime");
    }
    //Get an Employee
    public void setEmployee() {
        //create an annonymos employee
        employee.setName(txname.getText().toString());
        employee.setPosition(spinnerPosition.getSelectedItem().toString());
        employee.setBirthday(txBirthDay.getText().toString());
        employee.setLeaveDate(txLeaveDate.getText().toString());
        employee.setJoinDate(txJoinDate.getText().toString());
        employee.setAddress(spinnerPlaceOfBirth.getSelectedItem().toString());
        employee.setDepartment(spinnerDepartment.getSelectedItem().toString());
        employee.setPhone(txPhone.getText().toString());
        //employee.setImage(selectedImageUri.getPath().toString());
        employee.setStatus(spinnerStatus.getSelectedItem().toString());
        //TODO Update to database
    }
    public void addEmployee(){

    }
    public void updateEmployee(){

    }
    /******
     * Set spinners
     *************/
    public void setPlaceOfBirthSpinner(String place) {
        spinnerPlaceOfBirth.setSelection(adapterSpinerPlaceOfBirth.getPosition(place));
        spinnerPlaceOfBirth.setEnabled(false);
    }

    public void setDepartmentSpinner(String depart) {
        spinnerDepartment.setSelection(adapterSpinerDepartment.getPosition(depart));
        spinnerDepartment.setEnabled(false);
    }

    public void setPositionSpinner(String position) {
        spinnerPosition.setSelection(adapterSpinerPosition.getPosition(position));
        spinnerPosition.setEnabled(false);
    }

    public void setStatusSpinner(String status) {
        spinnerStatus.setSelection(adapterSpinerStatus.getPosition(status));
        spinnerStatus.setEnabled(false);
    }

    /******
     * Edit and Add employee mode
     *************/
    public void enableEditEmployee() {
        //Set name
        txname.setEnabled(true);
        //Set Place of birth
        spinnerPlaceOfBirth.setEnabled(true);
        //Set birthday
        txBirthDay.setEnabled(true);
        //Set Phone
        txPhone.setEnabled(true);
        //SetPosition
        spinnerPosition.setEnabled(true);
        //SetDepartment
        spinnerDepartment.setEnabled(true);
        //SetStatus
        spinnerStatus.setEnabled(true);
        //SetJoinDate
        txJoinDate.setEnabled(true);
        //SetLeaveDate
        txLeaveDate.setEnabled(true);
        //Set avatar
        avartar.setEnabled(true);
    }

    //Set buttons invisible, according to the request, change the status
    public void setButton(int mode) {
        btupdate.setVisibility(View.INVISIBLE);
        btCancel.setVisibility(View.INVISIBLE);
        btEdit.setVisibility(View.INVISIBLE);
        btAdd.setVisibility(View.INVISIBLE);
        btCancelAdd.setVisibility(View.INVISIBLE);
        switch (mode) {
            case VIEWMODE:
                btEdit.setVisibility(View.VISIBLE);
                break;
            case EDITMODE:
                btCancel.setVisibility(View.VISIBLE);
                btupdate.setVisibility(View.VISIBLE);
                break;
            case ADDMODE:
                btAdd.setVisibility(View.VISIBLE);
                btCancelAdd.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }

    public void setButtonListenner() {
        btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File root = Environment.getExternalStorageDirectory();
                File saveDir = new File(root.getAbsolutePath() + File.separator + "EMSPhoto");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                saveFile(selectedImageUri, saveDir);
                setEmployee();
                updateEmployee();
                displayEmployeeDetail();
                setButton(VIEWMODE);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEmployeeDetail();
                setButton(VIEWMODE);
            }
        });
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditEmployee();
                setButton(EDITMODE);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File root = Environment.getExternalStorageDirectory();
                File saveDir = new File(root.getAbsolutePath() + File.separator + "EMSPhoto");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                saveFile(selectedImageUri, saveDir);
                setEmployee();
                addEmployee();
                displayEmployeeDetail();
                setButton(VIEWMODE);
            }
        });
        btCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        avartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
        txJoinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txJoinDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                };
                DatePickerDialog joinDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                        callback, mYear, mMonth, mDay);
                joinDateDialog.show();
            }
        });
        txLeaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txLeaveDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                };
                DatePickerDialog leaveDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                        callback, mYear, mMonth, mDay);
                leaveDateDialog.show();
            }
        });
        txBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txBirthDay.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                };
                DatePickerDialog birthDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                        callback, mYear, mMonth, mDay);
                birthDateDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
