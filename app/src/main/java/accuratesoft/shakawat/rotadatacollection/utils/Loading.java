package accuratesoft.shakawat.rotadatacollection.utils;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class Loading {
    Button btn;
    ProgressBar pBar;
    public Loading(Button btn, ProgressBar pBar){
        this.btn = btn;
        this.pBar = pBar;
    }
    public void alterVisibility(){
        if (btn.getVisibility()== View.VISIBLE){
            this.btn.setVisibility(View.GONE);
            this.pBar.setVisibility(View.VISIBLE);
        }else {
            this.btn.setVisibility(View.VISIBLE);
            this.pBar.setVisibility(View.GONE);
        }
    }
}
