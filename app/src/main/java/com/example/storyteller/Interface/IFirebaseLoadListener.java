package com.example.storyteller.Interface;

import com.example.storyteller.Model.ItemGroup;

import java.util.List;

public interface IFirebaseLoadListener {

    void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList);
    void onFirebaseLoadFailed(String message);

  //  void onFirebaseLoadSuccess(List<ItemGroup> itemGroups);
}
