package com.ruet_cse_1503050.ragib.bottomsheetmenu;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetMenu {

    private final Context context;

    private final int textColorPrimary;
    private final int iconTint;
    private final int arrowTint;

    private final Menu main_menu;
    private final BottomSheetListView menu_list;
    private final BottomSheetDialog dialog;

    public BottomSheetMenu(@NonNull final Context context,
                           final int menuRes,
                           @NonNull final BottomSheetMenuListener listener,
                           @Nullable final View initial_custom_header,
                           @Nullable final Drawable subjectDrawable,
                           @Nullable final String subjectTitle,
                           final int menuBackground,
                           final int menuDividerColor,
                           final int textColorPrimary,
                           final int textColorSecondary,
                           final int iconTint,
                           final int arrowTint) {


        this.context = context;
        this.textColorPrimary = textColorPrimary;
        this.iconTint = iconTint;
        this.arrowTint = arrowTint;

        View main_view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_list_view, null, false);
        this.menu_list = main_view.findViewById(R.id.menu_list);
        View menu_header = main_view.findViewById(R.id.menu_header);

        final LinearLayout optional_view = menu_header.findViewById(R.id.optional_view);

        final LinearLayout subject_desc_holder = menu_header.findViewById(R.id.subject_desc_holder);
        final ImageView subject_icon = menu_header.findViewById(R.id.subject_icon);
        final TextView subject_name = menu_header.findViewById(R.id.subject_title);
        final TextView submenu_name = menu_header.findViewById(R.id.submenu_title);

        final View anchor_view = menu_header.findViewById(R.id.anchor_view);

        if (initial_custom_header != null || subjectDrawable != null || subjectTitle != null) {

            // at this point, everything in the header part is in 'GONE' state

            if (initial_custom_header != null) {

                // initially remove the subject_desc_holder while displaying custom header
                subject_desc_holder.setVisibility(View.GONE);

                // add the custom header to the optional view
                optional_view.addView(initial_custom_header);
                optional_view.setVisibility(View.VISIBLE);

            }

            if (subjectDrawable != null) {

                // put the drawable in the subject_icon and make it visible
                subject_icon.setImageDrawable(subjectDrawable);
                subject_icon.setVisibility(View.VISIBLE);

            }

            if (subjectTitle != null) {

                // put the title in the subject_name, set text color and make it visible
                subject_name.setTextColor(this.textColorPrimary);
                subject_name.setText(subjectTitle);
                subject_name.setVisibility(View.VISIBLE);

            }

            // make divider anchor visible
            anchor_view.setVisibility(View.VISIBLE);
        }

        this.menu_list.setDivider(
                menuDividerColor != 0 ?
                        new GradientDrawable(
                                GradientDrawable.Orientation.LEFT_RIGHT,
                                new int[]{menuDividerColor, menuDividerColor, menuDividerColor}
                        ) : new AttributeResolver(context).getDrawable(R.attr.dividerHorizontal)
        );
        menu_header.setBackgroundColor(menuBackground);
        this.menu_list.setBackgroundColor(menuBackground);
        this.menu_list.setDividerHeight(1);
        this.menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem item = (MenuItem) menu_list.getItemAtPosition(position);
                if (item != null) {
                    if (item.hasSubMenu()) {

                        // remove optional view before showing sub-menu
                        optional_view.setVisibility(View.GONE);


                        submenu_name.setTextColor(textColorSecondary);
                        submenu_name.setText(item.getTitle());
                        submenu_name.setVisibility(View.VISIBLE);

                        // make subject_desc_header visible
                        subject_desc_holder.setVisibility(View.VISIBLE);

                        // make divider anchor visible
                        anchor_view.setVisibility(View.VISIBLE);

                        BottomSheetMenu.this.show(item);
                    } else {

                        // fire the listener and dissmiss the menu dialog
                        listener.onMenuItemSelected(item);
                        dialog.dismiss();

                    }
                }
            }
        });

        this.dialog = new BottomSheetDialog(context);
        this.dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog underlying_dialog = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = underlying_dialog.findViewById(R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                    BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
                    BottomSheetBehavior.from(bottomSheet).setHideable(true);
                }
            }
        });

        this.dialog.setContentView(main_view);

        PopupMenu menuBuilder = new PopupMenu(context, null);
        menuBuilder.inflate(menuRes);

        this.main_menu = menuBuilder.getMenu();

    }

    public BottomSheetMenu(@NonNull final Context context,
                           @NonNull final Menu menu,
                           @NonNull final BottomSheetMenuListener listener,
                           @Nullable final View initial_custom_header,
                           @Nullable final Drawable subjectDrawable,
                           @Nullable final String subjectTitle,
                           final int menuBackground,
                           final int menuDividerColor,
                           final int textColorPrimary,
                           final int textColorSecondary,
                           final int iconTint,
                           final int arrowTint) {


        this.context = context;
        this.textColorPrimary = textColorPrimary;
        this.iconTint = iconTint;
        this.arrowTint = arrowTint;

        View main_view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_list_view, null, false);
        this.menu_list = main_view.findViewById(R.id.menu_list);
        View menu_header = main_view.findViewById(R.id.menu_header);

        final LinearLayout optional_view = menu_header.findViewById(R.id.optional_view);

        final LinearLayout subject_desc_holder = menu_header.findViewById(R.id.subject_desc_holder);
        final ImageView subject_icon = menu_header.findViewById(R.id.subject_icon);
        final TextView subject_name = menu_header.findViewById(R.id.subject_title);
        final TextView submenu_name = menu_header.findViewById(R.id.submenu_title);

        final View anchor_view = menu_header.findViewById(R.id.anchor_view);

        if (initial_custom_header != null || subjectDrawable != null || subjectTitle != null) {

            // at this point, everything in the header part is in 'GONE' state

            if (initial_custom_header != null) {

                // initially remove the subject_desc_holder while displaying custom header
                subject_desc_holder.setVisibility(View.GONE);

                // add the custom header to the optional view
                optional_view.addView(initial_custom_header);
                optional_view.setVisibility(View.VISIBLE);

            }

            if (subjectDrawable != null) {

                // put the drawable in the subject_icon and make it visible
                subject_icon.setImageDrawable(subjectDrawable);
                subject_icon.setVisibility(View.VISIBLE);

            }

            if (subjectTitle != null) {

                // put the title in the subject_name, set text color and make it visible
                subject_name.setTextColor(this.textColorPrimary);
                subject_name.setText(subjectTitle);
                subject_name.setVisibility(View.VISIBLE);

            }

            // make divider anchor visible
            anchor_view.setVisibility(View.VISIBLE);
        }

        this.menu_list.setDivider(
                menuDividerColor > 0 ?
                        new GradientDrawable(
                                GradientDrawable.Orientation.LEFT_RIGHT,
                                new int[]{menuDividerColor, menuDividerColor, menuDividerColor}
                        ) : new AttributeResolver(context).getDrawable(R.attr.dividerHorizontal)
        );
        menu_header.setBackgroundColor(menuBackground);
        this.menu_list.setBackgroundColor(menuBackground);
        this.menu_list.setDividerHeight(1);
        this.menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem item = (MenuItem) menu_list.getItemAtPosition(position);
                if (item != null) {
                    if (item.hasSubMenu()) {

                        // remove optional view before showing sub-menu
                        optional_view.setVisibility(View.GONE);


                        submenu_name.setTextColor(textColorSecondary);
                        submenu_name.setText(item.getTitle());
                        submenu_name.setVisibility(View.VISIBLE);

                        // make subject_desc_header visible
                        subject_desc_holder.setVisibility(View.VISIBLE);

                        // make divider anchor visible
                        anchor_view.setVisibility(View.VISIBLE);

                        BottomSheetMenu.this.show(item);
                    } else {

                        // fire the listener and dissmiss the menu dialog
                        listener.onMenuItemSelected(item);
                        dialog.dismiss();

                    }
                }
            }
        });

        this.dialog = new BottomSheetDialog(context);
        this.dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog underlying_dialog = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = underlying_dialog.findViewById(R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                    BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
                    BottomSheetBehavior.from(bottomSheet).setHideable(true);
                }
            }
        });

        this.dialog.setContentView(main_view);

        this.main_menu = menu;

    }

    public void show() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        List<MenuItem> items = new ArrayList<>(0);
        for (int i = 0; i < main_menu.size(); ++i) {
            if (main_menu.getItem(i).isVisible()) {
                items.add(main_menu.getItem(i));
            }
        }
        menu_list.setAdapter(new MenuListAdapter(context, R.layout.menu_holder_layout, items));
        dialog.show();
    }

    private boolean show(MenuItem item) {
        boolean validToShow = true;
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (item != null) {
            if (item.hasSubMenu()) {
                List<MenuItem> items = new ArrayList<>(0);
                for (int i = 0; i < item.getSubMenu().size(); ++i) {
                    if (item.getSubMenu().getItem(i).isVisible()) {
                        items.add(item.getSubMenu().getItem(i));
                    }
                }
                menu_list.setAdapter(new MenuListAdapter(context, R.layout.menu_holder_layout, items));
            } else {
                validToShow = false;
            }
        } else {
            validToShow = false;
        }
        if (validToShow) {
            dialog.show();
        }
        return validToShow;
    }

    public boolean isShowing() {
        return this.dialog.isShowing();
    }

    public void dismiss() {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    private class MenuListAdapter extends ArrayAdapter<MenuItem> {

        MenuListAdapter(@NonNull Context context, int resource, @NonNull List<MenuItem> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            MenuHolder holder;
            if (convertView == null) {
                convertView =
                        LayoutInflater.from(getContext())
                                .inflate(
                                        R.layout.menu_holder_layout,
                                        null,
                                        false
                                );
                convertView.setTag(holder = new MenuHolder(convertView));
            } else {
                holder = (MenuHolder) convertView.getTag();
            }

            MenuItem item = getItem(position);
            if (item != null) {
                holder.menu_icon.setImageDrawable(
                        item.getIcon() != null ?
                                item.getIcon() :
                                ContextCompat.getDrawable(context, R.drawable.placeholder_menu_icon)
                );
                holder.menu_title.setText(item.getTitle());
                holder.more_menu_indicator.setImageDrawable(
                        item.hasSubMenu() ?
                                ContextCompat.getDrawable(context, R.drawable.arrow_right) :
                                null
                );
            }

            return item != null ? convertView : null;
        }

        private final class MenuHolder {
            TextView menu_title;
            ImageView menu_icon;
            ImageView more_menu_indicator;

            MenuHolder(View parent) {
                menu_icon = parent.findViewById(R.id.menu_icon);
                menu_title = parent.findViewById(R.id.menu_title);
                more_menu_indicator = parent.findViewById(R.id.more_menu_indicator);
                menu_title.setTextColor(BottomSheetMenu.this.textColorPrimary);
                menu_icon.setColorFilter(BottomSheetMenu.this.iconTint);
                more_menu_indicator.setColorFilter(BottomSheetMenu.this.arrowTint);
            }
        }

    }

}
