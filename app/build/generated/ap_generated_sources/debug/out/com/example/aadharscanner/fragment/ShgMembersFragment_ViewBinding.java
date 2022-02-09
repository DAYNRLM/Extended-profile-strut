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

public class ShgMembersFragment_ViewBinding implements Unbinder {
  private ShgMembersFragment target;

  @UiThread
  public ShgMembersFragment_ViewBinding(ShgMembersFragment target, View source) {
    this.target = target;

    target.shgMemberRecyclerview = Utils.findOptionalViewAsType(source, R.id.shgMemberRecyclerview, "field 'shgMemberRecyclerview'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShgMembersFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.shgMemberRecyclerview = null;
  }
}
