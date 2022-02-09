// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ShgFragment_ViewBinding implements Unbinder {
  private ShgFragment target;

  @UiThread
  public ShgFragment_ViewBinding(ShgFragment target, View source) {
    this.target = target;

    target.slectShgMemberListRV = Utils.findOptionalViewAsType(source, R.id.slectShgMemberListRV, "field 'slectShgMemberListRV'", RecyclerView.class);
    target.location_TV = Utils.findOptionalViewAsType(source, R.id.location_TV, "field 'location_TV'", TextView.class);
    target.add_memberTV = Utils.findOptionalViewAsType(source, R.id.add_memberTV, "field 'add_memberTV'", ImageView.class);
    target.shg_name = Utils.findOptionalViewAsType(source, R.id.shg_name, "field 'shg_name'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShgFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.slectShgMemberListRV = null;
    target.location_TV = null;
    target.add_memberTV = null;
    target.shg_name = null;
  }
}
