package com.moutamid.gbplusversion.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.gbplusversion.R;
import com.moutamid.gbplusversion.adapters.HappyAdapter;
import com.moutamid.gbplusversion.adapters.OtherAdapter;

public class OtherEmotFragment extends Fragment {
    private String[] OtherAscii = new String[]{"( ཀ ʖ̯ ཀ)", "(╥﹏╥)", "( ͡°ᴥ ͡° ʋ)", "ಠ_ಥ", "ಠ~ಠ", "ʕ◉.◉ʔ", "༼ つ ◕_◕ ༽つ", "(ಥ﹏ಥ)", "ヾ(⌐■_■)ノ", "( ︶︿︶)", "(✖╭╮✖)", "༼ つ ಥ_ಥ ༽つ", "(\"º _ º)", "┬─┬ノ( º _ ºノ)", "(._.) ( l: ) ( .-. ) ( :l ) (._.)", "༼ ºل͟º ༼ ºل͟º ༼ ºل͟º ༽ ºل͟º ༽ ºل͟º ༽", "┬┴┬┴┤(･_├┬┴┬┴", "(°ロ°)☝", "(▀̿Ĺ̯▀̿ ̿)", "﴾͡๏̯͡๏﴿ O'RLY?", "⚆ _ ⚆", "ಠ‿↼", "☼.☼", "◉_◉", "( ✧≖ ͜ʖ≖)", "( ͠° ͟ʖ ͡°)", "( ‾ ʖ̫ ‾)", "(͡ ͡° ͜ つ ͡͡°)", "( ﾟдﾟ)", "┬──┬ ノ( ゜-゜ノ)", "¯\\(°_o)/¯", "(ʘᗩʘ')", "☜(⌒▽⌒)☞", "(;´༎ຶД༎ຶ`)", "̿̿ ̿̿ ̿̿ ̿'̿'\\̵͇̿̿\\з= ( ▀ ͜͞ʖ▀) =ε/̵͇̿̿/’̿’̿ ̿ ̿̿ ̿̿ ̿̿", "[̲̅$̲̅(̲̅5̲̅)̲̅$̲̅]", "(ᇂﮌᇂ)", "(≧ω≦)", "►_◄", "أ ̯ أ", "ლ(╹ε╹ლ)", "ᇂ_ᇂ", "＼(￣ー＼)(／ー￣)／", "♪┏ ( ･o･) ┛♪┗ (･o･ ) ┓♪┏(･o･)┛♪", "╘[◉﹃◉]╕", "( ･_･)♡", "(¤﹏¤)", "( ˘︹˘ )"};

    public OtherEmotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_emot, container, false);
        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        list.setHasFixedSize(true);
        OtherAdapter adapter = new OtherAdapter(this.OtherAscii, view.getContext());
        list.setAdapter(adapter);
        return view;
    }
}