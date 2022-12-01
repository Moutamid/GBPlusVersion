package com.moutamid.gb.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.gb.R;
import com.moutamid.gb.adapters.AngryAdapter;

public class AngryEmotFragment extends Fragment {
    private String[] angryAscii = new String[]{"(≖︿≖ )", "(ง ͠° ͟ل͜ ͡°)ง", "ಠ_ಠ", "( ͠°Д°͠ )", "ლ(ಠ益ಠლ)", "(>ლ)", "(ง'-̀'́)ง", "(ノಠ益ಠ)ノ彡┻━┻", "(╯°□°）╯︵( .o.)", "(╯°□°）╯︵ /( ‿⌓‿ )\\", "(╯°□°）╯︵ ┻━┻", "┻━┻ ︵ヽ(`Д´)ﾉ︵ ┻━┻", "(┛◉Д◉)┛彡┻━┻ ", "(¬_¬)", "ಠoಠ", "ᕙ(⇀‸↼‶)ᕗ", "ᕦ(ò_óˇ)ᕤ", "¯\\_(ʘ_ʘ)_/¯", "( ͡°_ʖ ͡°)", "( ° ͜ʖ͡°)╭∩╮", "( ͡°Ĺ̯ ͡° )", "( ͠° ͟ʖ °͠ )", "¯\\_( ͠° ͟ʖ °͠ )_/¯", "( ͡°⊖ ͡°)", "ರ_ರ", "(>人<)", "ಠ╭╮ಠ", "(◣_◢)", "(⋋▂⋌)", "〴⋋_⋌〵", "(╹◡╹)凸", "ლ(͠°◞౪◟°͠ლ)", "╭∩╮(︶︿︶)╭∩╮", "(ㆆ_ㆆ)"};

    public AngryEmotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_angry_emot, container, false);
        RecyclerView list = view.findViewById(R.id.list);


        list.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        list.setHasFixedSize(true);
        AngryAdapter adapter = new AngryAdapter(this.angryAscii, view.getContext());
        list.setAdapter(adapter);
        return view;
    }
}