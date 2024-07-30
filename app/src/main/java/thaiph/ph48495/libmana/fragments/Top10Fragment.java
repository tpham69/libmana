package thaiph.ph48495.libmana.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.adapters.TopAdapter;
import thaiph.ph48495.libmana.daos.ThongKeDAO;
import thaiph.ph48495.libmana.models.Top;


public class Top10Fragment extends Fragment {

    private ArrayList<Top> list;
    private ThongKeDAO dao;

    private TopAdapter topAdapter;
    private ListView lvTop10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10, container, false);

        lvTop10 = view.findViewById(R.id.lvTop10);
        dao = new ThongKeDAO(getContext());

        //get data
        list = dao.getTop();

        //set adapter
        topAdapter = new TopAdapter(getContext(), list);
        lvTop10.setAdapter(topAdapter);

        return view;
    }
}