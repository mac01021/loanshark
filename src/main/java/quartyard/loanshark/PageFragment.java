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
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;


import java.text.DecimalFormat;
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

	boolean _updating = false;

	static DecimalFormat _numeral = new DecimalFormat();
	static DecimalFormat _parse = new DecimalFormat();



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
		_numeral.setGroupingUsed(false);
		_parse.setGroupingUsed(false);

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
		initDisplay();
		
		return rootView;
	}

	void refreshDisplay() {
		if (_updating) return;
		_updating = true;

		Value target = _loan.getToCompute();
		if (target == Value.principal) {
			_txtPrincipal.setText(numeral(_loan.getPrincipal()));
		}
		if (target == Value.ari) {
			_txtAPR.setText(numeral(_loan.getARI() * 100));
		}
		if (target == Value.payment) {
			_txtPayment.setText(numeral(_loan.getPayment()));
		}
		if (target == Value.length) {
			displayLength();
		}
		_txtNbPayments.setText(numeral(_loan.getNbPayments()));

		_updating = false;
	}

	void initDisplay() {
		_updating = true;
		_txtPrincipal.setText(numeral(_loan.getPrincipal()));
		_txtAPR.setText(numeral(_loan.getARI() * 100));
		_spnFreq.setSelection(_loan.getFrequency().ordinal());
		_txtPayment.setText(numeral(_loan.getPayment()));
		displayLength();
		_txtNbPayments.setText(numeral(_loan.getNbPayments()));
		_cbs.get(_loan.getToCompute()).performClick();
		_updating = false;
	}

	void displayLength() {
		double len = _loan.getLength();
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
		_txtLength.setText(numeral(len));
	}

	String numeral(double n) {
		return _numeral.format(n);
	}

	double parse(CharSequence e) {
		try {
			String s = e.toString();
			s = s.replaceAll(",", "");
			return _parse.parse(s.toString()).doubleValue();
		} catch (Exception x) {
			return 0;
		}
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
					cb.setChecked(true);
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
		_txtPrincipal.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable x) {
				_loan.setPrincipal(parse(x));
				refreshDisplay();
			}
			public void beforeTextChanged(CharSequence s, int st, int cnt, int after) {}
			public void onTextChanged(CharSequence s, int st, int before, int cnt) {}
		});
		_txtAPR.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable x) {
				_loan.setARI(parse(x)/100);
				refreshDisplay();
			}
			public void beforeTextChanged(CharSequence s, int st, int cnt, int after) {}
			public void onTextChanged(CharSequence s, int st, int before, int cnt) {}
		});
		_txtPayment.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable x) {
				_loan.setPayment(parse(x));
				refreshDisplay();
			}
			public void beforeTextChanged(CharSequence s, int st, int cnt, int after) {}
			public void onTextChanged(CharSequence s, int st, int before, int cnt) {}
		});
		_txtLength.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable x) {
				TimeUnit unit = ((TimeUnit)_spnLength.getSelectedItem());
				_loan.setLoanLength(parse(x) * unit._nbDays);
				refreshDisplay();
			}
			public void beforeTextChanged(CharSequence s, int st, int cnt, int after) {}
			public void onTextChanged(CharSequence s, int st, int before, int cnt) {}
		});
		_spnFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> v, View w, int x, long y) {
				_loan.setFrequency((Frequency)_spnFreq.getSelectedItem());
				refreshDisplay();
			}
			public void onNothingSelected(AdapterView<?> v) {}
		});
		_spnLength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> v, View w, int x, long y) {
				TimeUnit unit = ((TimeUnit)_spnLength.getSelectedItem());
				double nb = parse(_txtLength.getText().toString());
				_loan.setLoanLength(nb * unit._nbDays);
				refreshDisplay();
			}
			public void onNothingSelected(AdapterView<?> v) {}
		});
	}
}

