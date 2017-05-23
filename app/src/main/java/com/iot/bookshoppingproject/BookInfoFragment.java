/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iot.bookshoppingproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Provides UI for the view with Cards.
 */
public class BookInfoFragment extends Fragment {

    EditText editTextTitle;
    EditText editTextPrice;
    EditText editTextISBN;
    Button buttonAddBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup fragment = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_camera, container, false);

        editTextTitle = (EditText) fragment.findViewById(R.id.editTextTitle);
        editTextPrice = (EditText) fragment.findViewById(R.id.editTextPrice);
        editTextISBN = (EditText) fragment.findViewById(R.id.editTextBarcode);
        buttonAddBook = (Button) fragment.findViewById(R.id.btnAddbook);

        return fragment;
    }
}

