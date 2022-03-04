package com.example.smartlens.Driver;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartlens.R;

public class DriverHelp extends Fragment {
Map<String,List<String>> listHashMap;
List<String> childItem;
private ExpandableListView expandableListView;

List<String> ParentList = new ArrayList<>();
    {
        ParentList.add("How can i Upload My Document's?");
        ParentList.add("How can i view my Document's and it's verification status?");
        ParentList.add("How can i Delete my Document's?");
        ParentList.add("How can i view memo?");
        ParentList.add("How can i pay fine?");
        ParentList.add("How can i change my profile Detail's?");
        ParentList.add("How can i change my Password?");
        ParentList.add("How can i Permanently Delete my Account?");
        ParentList.add("Other Questions?");
    }
    String[] class1 = {"Click on Upload Documents > Enter Details and Take a Picture of Document's > Click on Upload."};
    String[] class2 = {"Click on My Document's."};
    String[] class3 = {"Click on My Document's > Long Press on Document Which you Want to Delete > Click on Delete Document."};
    String[] class4 = {"Click on View Memo."};
    String[] class5 = {"Click on View Memo > Select Memo and Open it > Click on Pay Now > Enter Card or Upi Detail's > Click on Pay Button."};
    String[] class6 = {"Open Profile > Click on Edit Profile Button > Enter Changes > Click on Save Button."};
    String[] class7 = {"Click on Three Dot given in Title Bar > Click on Change Password > Enter New Password and Click on Save."};
    String[] class8 = {"Click on Three Dot given in Title Bar > Click on Delete Account Permanently."};
    String[] class9 = {"For further Question you can Contact us on our mail : smartlenscustomercare.com"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_help, container, false);

        expandableListView = v.findViewById(R.id.d_expandablelistview);
        listHashMap = new LinkedHashMap<>();


        for(String HoldItem:ParentList){
            if(HoldItem.equals("How can i Upload My Document's?")){
                loadChild(class1);
            }
            else if(HoldItem.equals("How can i view my Document's and it's verification status?")){
                loadChild(class2);
            }
            else if(HoldItem.equals("How can i Delete my Document's?")){
                loadChild(class3);
            }
            else if(HoldItem.equals("How can i view memo?")){
                loadChild(class4);
            }
            else if(HoldItem.equals("How can i pay fine?")){
                loadChild(class5);
            }
            else if(HoldItem.equals("How can i change my profile Detail's?")){
                loadChild(class6);
            }
            else if(HoldItem.equals("How can i change my Password?")){
                loadChild(class7);
            }
            else if(HoldItem.equals("How can i Permanently Delete my Account?")){
                loadChild(class8);
            }
            else if(HoldItem.equals("Other Questions?")){
                loadChild(class9);
            }
            listHashMap.put(HoldItem,childItem);
        }
        ExpandableListAdapter adapter = new DriverExpandableListViewAdapter(getActivity(),ParentList,(HashMap<String,List<String>>) listHashMap);
        expandableListView.setAdapter(adapter);
        return v;
    }
    private void loadChild(String[] parentElementName){
        childItem = new ArrayList<>();
        Collections.addAll(childItem,parentElementName);
    }
}

