package com.akash.draganddrop;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
{
    String TAG="Main Activity";

    @InjectView(R.id.button1)
    Button button1;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;

    @InjectView(R.id.button_match1)
    Button match1;
    @InjectView(R.id.button_match2)
    Button match2;
    @InjectView(R.id.button_match3)
    Button match3;
    @InjectView(R.id.button_match4)
    Button match4;
    @InjectView(R.id.button_match5)
    Button match5;
    @InjectView(R.id.button_match6)
    Button match6;

    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        button1.setOnTouchListener(new RightTouchListener());
        button2.setOnTouchListener(new RightTouchListener());
        button3.setOnTouchListener(new RightTouchListener());
        button4.setOnTouchListener(new RightTouchListener());
        button5.setOnTouchListener(new RightTouchListener());
        button6.setOnTouchListener(new RightTouchListener());

        match1.setOnDragListener(new LeftDragListener());
        match2.setOnDragListener(new LeftDragListener());
        match3.setOnDragListener(new LeftDragListener());
        match4.setOnDragListener(new LeftDragListener());
        match5.setOnDragListener(new LeftDragListener());
        match6.setOnDragListener(new LeftDragListener());
    }

    public class RightTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction()==event.ACTION_DOWN)
            {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                return true;
            }
            else {
                return false;
            }
        }
    }

    public class LeftDragListener implements View.OnDragListener
    {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case  DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View view=(View)event.getLocalState();
                    Button dropTarget=(Button)v;
                    Button dropped=(Button)view;
                    if(dropTarget.getText().toString()==dropped.getText().toString())
                    {
                        view.setVisibility(View.INVISIBLE);

                        Object tag=dropTarget.getTag();

                        if(tag!=null)
                        {
                            int ID=(Integer)tag;
                            findViewById(ID).setVisibility(View.VISIBLE);
                        }
                        dropTarget.setTag(dropped.getId());
                        dropTarget.setOnDragListener(null);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,dropTarget.getText().toString()+" does not match",Toast.LENGTH_SHORT).show();
                       vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;

            }
            return true;
        }
    }

}

