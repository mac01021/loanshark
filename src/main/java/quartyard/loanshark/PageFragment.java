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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


import java.util.EnumMap;

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
		View rootView = inflater.inflate(R.layout.page, container, false);
		((Spinner) rootView.findViewById(quartyard.loanshark.R.id.loan_len_spinner))
			.setAdapter(new ArrayAdapter<TimeUnit>(this.getActivity(), 
							       android.R.layout.simple_spinner_item, 
							       TimeUnit.values()));
		((Spinner) rootView.findViewById(quartyard.loanshark.R.id.freq_spinner))
			.setAdapter(new ArrayAdapter<Frequency>(this.getActivity(), 
							       android.R.layout.simple_spinner_item, 
							       Frequency.values()));
		((EditText) rootView.findViewById(quartyard.loanshark.R.id.nb_payments_textbox))
		 	.setEnabled(false);
		addCheckListeners(rootView);
		addTextListeners(rootView);
		return rootView;
	}

	void addCheckListeners(View view) {
		final EnumMap<Value, CheckBox> cbs = new EnumMap(Value.class);
		cbs.put(Value.principal, (CheckBox)view.findViewById(quartyard.loanshark.R.id.principal_checkbox));
		cbs.put(Value.ari, (CheckBox)view.findViewById(quartyard.loanshark.R.id.apr_checkbox));
		cbs.put(Value.payment, (CheckBox)view.findViewById(quartyard.loanshark.R.id.payment_amt_checkbox));
		cbs.put(Value.length, (CheckBox)view.findViewById(quartyard.loanshark.R.id.loan_length_checkbox));
		final EnumMap<Value, EditText> tbs = new EnumMap(Value.class);
		tbs.put(Value.principal, (EditText)view.findViewById(quartyard.loanshark.R.id.principal_textbox));
		tbs.put(Value.ari, (EditText)view.findViewById(quartyard.loanshark.R.id.apr_textbox));
		tbs.put(Value.payment, (EditText)view.findViewById(quartyard.loanshark.R.id.payment_amt_textbox));
		tbs.put(Value.length, (EditText)view.findViewById(quartyard.loanshark.R.id.loan_length_textbox));
		final EnumMap<Value, Spinner>  spinners = new EnumMap(Value.class);
		spinners.put(Value.length, (Spinner)view.findViewById(quartyard.loanshark.R.id.loan_len_spinner));

		for(Value v : cbs.keySet()) {
			final Value val = v;
			final CheckBox cb = cbs.get(val);
			cb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View x) {
					for (Value v :cbs.keySet()) {
						if (v != val) {
							CheckBox othr = cbs.get(v);
							othr.setChecked(false);
						}
					}
					for (Value v : tbs.keySet()) {
						EditText tb = tbs.get(v);
						if (v != val) {
							tb.setEnabled(true);
						} else {
							tb.setEnabled(false);
						}
					}
					for (Value v : spinners.keySet()) {
						Spinner s = spinners.get(v);
						if (v != val) {
							s.setEnabled(true);
						} else {
							s.setEnabled(false);
						}
					}
				}
			});
		}
	}

	void addTextListeners(View view) {

	}
}

