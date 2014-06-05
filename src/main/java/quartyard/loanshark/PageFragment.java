/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quartyard.loanshark;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;



/**
 *
 * @author mac01021
 */
public class PageFragment extends Fragment {
    
    //Loan _loan;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //_loan = new Loan();
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.page, container, false);
        ((Spinner) rootView.findViewById(quartyard.loanshark.R.id.loan_len_spinner))
                .setAdapter(new ArrayAdapter<TimeUnit>(this.getActivity(), 
                                                       android.R.layout.simple_spinner_item, 
                                                       TimeUnit.values()));
        ((Spinner) rootView.findViewById(quartyard.loanshark.R.id.freq_spinner))
                .setAdapter(new ArrayAdapter<Frequency>(this.getActivity(), 
                                                       android.R.layout.simple_spinner_item, 
                                                       Frequency.values()));
        return rootView;
    }
}
