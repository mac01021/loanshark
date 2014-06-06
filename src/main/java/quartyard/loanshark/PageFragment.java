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

	Loan _loan;

	CheckBox _chkPrincipal, _chkAPR, _chkPayment, _chkLength;
	EditText _txtPrincipal, _txtAPR, _txtPayment, _txtLength, _txtNbPayments;
	Spinner  _spnFreq, _spnLength;
	final EnumMap<Value, CheckBox> _cbs = new EnumMap(Value.class);
	final EnumMap<Value, EditText> _tbs = new EnumMap(Value.class);
	final EnumMap<Value, Spinner>  _spinners = new EnumMap(Value.class);

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		_loan = new Loan();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
				 ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.page, container, false);

		_chkPrincipal = (CheckBox) rootView.findViewById(quartyard.loanshark.R.id.principal_checkbox);
		_chkAPR = (CheckBox) rootView.findViewById(quartyard.loanshark.R.id.apr_checkbox);
		_chkPayment = (CheckBox) rootView.findViewById(quartyard.loanshark.R.id.payment_amt_checkbox);
		_chkLength = (CheckBox) rootView.findViewById(quartyard.loanshark.R.id.loan_length_checkbox);
		_txtPrincipal = (EditText) rootView.findViewById(quartyard.loanshark.R.id.principal_textbox);
		_txtAPR = (EditText) rootView.findViewById(quartyard.loanshark.R.id.apr_textbox);
		_txtPayment = (EditText) rootView.findViewById(quartyard.loanshark.R.id.payment_amt_textbox);
		_txtLength = (EditText) rootView.findViewById(quartyard.loanshark.R.id.loan_length_textbox);
		_txtNbPayments = (EditText) rootView.findViewById(quartyard.loanshark.R.id.nb_payments_textbox);
		_spnFreq = (Spinner) rootView.findViewById(quartyard.loanshark.R.id.freq_spinner);
		_spnLength = (Spinner) rootView.findViewById(quartyard.loanshark.R.id.loan_len_spinner);

		_spnLength.setAdapter(new ArrayAdapter<TimeUnit>(this.getActivity(), 
								 android.R.layout.simple_spinner_item, 
								 TimeUnit.values()));
		_spnFreq.setAdapter(new ArrayAdapter<Frequency>(this.getActivity(), 
							        android.R.layout.simple_spinner_item, 
							        Frequency.values()));
		_txtNbPayments.setEnabled(false);
		addCheckListeners();
		addUpateListeners();
		refreshDisplay();
		
		return rootView;
	}

	void refreshDisplay() {
		_txtPrincipal.setText(new Double(_loan.getPrincipal()).toString());
		_txtAPR.setText(new Double(_loan.getARI() * 100).toString());
		_spnFreq.setSelection(_loan.getFrequency().ordinal());
		_txtPayment.setText(new Double(_loan.getPayment()).toString());
		displayLength();
		_txtNbPayments.setText(new Double(_loan.getNbPayments()).toString());
		_cbs.get(_loan.getToCompute()).performClick();
	}

	void displayLength() {
		Double len = _loan.getLength();
		if (len > 5 * TimeUnit.Years._nbDays) {
			len /= TimeUnit.Years._nbDays;
			_spnLength.setSelection(TimeUnit.Years.ordinal());
		} else if (len > 2 * TimeUnit.Months._nbDays) {
			len /= TimeUnit.Months._nbDays;
			_spnLength.setSelection(TimeUnit.Months.ordinal());
		} else if (len > 2 * TimeUnit.Weeks._nbDays) {
			len /= TimeUnit.Weeks._nbDays;
			_spnLength.setSelection(TimeUnit.Weeks.ordinal());
		} else {
			_spnLength.setSelection(TimeUnit.Days.ordinal());
		}
		_txtLength.setText(len.toString());
	}

	void addCheckListeners() {
		_cbs.put(Value.principal, _chkPrincipal);
		_cbs.put(Value.ari, _chkAPR);
		_cbs.put(Value.payment, _chkPayment);
		_cbs.put(Value.length, _chkLength);
		_tbs.put(Value.principal, _txtPrincipal);
		_tbs.put(Value.ari, _txtAPR);
		_tbs.put(Value.payment, _txtPayment);
		_tbs.put(Value.length, _txtLength);
		_spinners.put(Value.length, _spnLength);

		for(Value v : _cbs.keySet()) {
			final Value val = v;
			final CheckBox cb = _cbs.get(val);
			cb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View x) {
					cb.setEnabled(false);
					for (Value v : _cbs.keySet()) {
						if (v != val) {
							CheckBox othr = _cbs.get(v);
							othr.setChecked(false);
							othr.setEnabled(true);
						}
					}
					for (Value v : _tbs.keySet()) {
						EditText tb = _tbs.get(v);
						if (v != val) {
							tb.setEnabled(true);
						} else {
							tb.setEnabled(false);
						}
					}
					for (Value v : _spinners.keySet()) {
						Spinner s = _spinners.get(v);
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

	void addUpateListeners() {

	}
}

