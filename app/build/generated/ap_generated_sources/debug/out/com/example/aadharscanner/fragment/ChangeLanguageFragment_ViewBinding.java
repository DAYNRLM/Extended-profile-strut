// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.fragment;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChangeLanguageFragment_ViewBinding implements Unbinder {
  private ChangeLanguageFragment target;

  @UiThread
  public ChangeLanguageFragment_ViewBinding(ChangeLanguageFragment target, View source) {
    this.target = target;

    target.changeLanguageRv = Utils.findOptionalViewAsType(source, R.id.changLanguage_Rv, "field 'changeLanguageRv'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangeLanguageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.changeLanguageRv = null;
  }
}
