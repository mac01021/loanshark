/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quartyard.loanshark;


import java.lang.Math;
import android.util.Log;

enum Value {
	principal, ari, payment, length;
}


public class Loan {
	
	Value _toCompute;
	double _principal, _foi, _payment, _length;
	Frequency _freq;
	int _nbPayments;
	
	public Loan(){
		_principal = 185000;
		_foi = Math.log(1.02875) / TimeUnit.Years._nbDays;
		_payment = 1200;
		_freq = Frequency.Monthly;
		_toCompute = Value.length;
		compute();
	}

	double gpp() {
		//growth per period
		// depends only on the force of interest
		double periodLength = _freq._unit._nbDays;
		double pforce = _foi * periodLength;
		return Math.exp(pforce);
	}
	
	void computePrincipal(){
		double f = gpp();
		double denom = 1 - f;
		double num = (1 / Math.pow(f, _nbPayments) - 1) * _payment;
		_principal = num / denom;
	}
	
	void computePayment(){
		double f = gpp();
		_payment = (_principal*(1-f)) / ((1/Math.pow(f, _nbPayments)) - 1);
	}
	
	void computeLength(){
		double f = gpp();
		double ppp = _principal / _payment;
		double temp = 1 / (ppp * (1-f) + 1);
		double logBaseF = Math.log(temp) / Math.log(f);
		_nbPayments = (int) Math.ceil(logBaseF);
		_length = _nbPayments * _freq._unit._nbDays;
	}

	void computeARI(){
		int x = 0;
		x /= x;
		//TODO
	}
	
	void compute(){
		Log.d("LoanSharkLoan", "Computing!");
		if (_toCompute == Value.principal){
			computePrincipal();
		}
		if (_toCompute == Value.ari) {
			computeARI();
		}
		if (_toCompute == Value.payment){
			computePayment();
		}
		if (_toCompute == Value.length){
			computeLength();
		}
	}

	public void compute(Value v) {
		_toCompute = v;
	}
	
	public void setPrincipal(double p){
		_principal = p;
		compute();
	}
	
	public void setARI(double ari){
		//Calculate the force of interest from the given annual rate of
		// interest
		double m = ari + 1;
		_foi = Math.log(m) / TimeUnit.Years._nbDays;
		compute();
	}
	
	public void setPayment(double p){
		_payment = p;
		compute();
	}
	
	public void setLoanLength(double len){
		_length = len;
		_nbPayments = (int) Math.ceil(len / _freq._unit._nbDays);
		compute();
	}
	
	public void setFrequency(Frequency f){
		_freq = f;
		compute();
	}

	public double getPrincipal() {
		return _principal;
	}

	public double getARI() {
		return Math.exp(_foi * TimeUnit.Years._nbDays) - 1;
	}

	public Frequency getFrequency() {
		return _freq;
	}

	public double getPayment() {
		return _payment;
	}

	public int getNbPayments() {
		return _nbPayments;
	}

	public double getLength() {
		return _length;
	}

	public Value getToCompute() {
		return _toCompute;
	}
	
}
