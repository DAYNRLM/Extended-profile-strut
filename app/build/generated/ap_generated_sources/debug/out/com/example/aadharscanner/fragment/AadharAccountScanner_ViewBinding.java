// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AadharAccountScanner_ViewBinding implements Unbinder {
  private AadharAccountScanner target;

  private View view7f090016;

  @UiThread
  public AadharAccountScanner_ViewBinding(final AadharAccountScanner target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.aadharScannerIV, "method 'imageView'");
    target.aadharScannerIV = Utils.castView(view, R.id.aadharScannerIV, "field 'aadharScannerIV'", ImageView.class);
    view7f090016 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.imageView();
      }
    });
    target.enterAadharEt = Utils.findOptionalViewAsType(source, R.id.enterAadharEt, "field 'enterAadharEt'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AadharAccountScanner target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.aadharScannerIV = null;
    target.enterAadharEt = null;

    view7f090016.setOnClickListener(null);
    view7f090016 = null;
  }
}
