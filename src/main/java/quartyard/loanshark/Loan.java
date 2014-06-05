/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quartyard.loanshark;

import java.util.EnumSet;
import java.util.EnumMap;


enum Value {
    principal, apr, payment, length;
}


public class Loan {
    //static final int VALCNT = 4;
    
    double _principal, _apr, _payment, _length;
    Frequency _freq;
    int _nbPayments;
    
    EnumSet<Value> _specified;
    
    public Loan(){
        _specified = EnumSet.noneOf(Value.class);
    }
    
    void freshen(Value v){
        _specified.add(v);
    }
    
    Value stalest(){
        int min = Integer.MAX_VALUE;
        Value mVal = Value.length;
        for(Value v:_specified){
            if (_staleness.get(v) < min) {
                mVal = v;
                min = _staleness.get(v);
            }
        }
        return mVal;
    }
    
    
    void computePrincipal(){
        _principal ++;
    }
    
    void computePayment(){
        _payment ++;
    }
    
    void computeLength(){
        _length ++;
    }
    
    void compute(){
        if (_specified.size() < Value.values().length - 1) {
            return;
        }
        Value toCompute = stalest();
        if (toCompute == Value.principal){
            computePrincipal();
        }
        if (toCompute == Value.payment){
            computePayment();
        }
        if (toCompute == Value.length){
            computeLength();
        }
        this._nbPayments = (int)(this._length / this._freq._unit._nbDays);
    }
    
    public void setPrincipal(double p){
        _principal = p;
        freshen(Value.principal);
        compute();
    }
    
    public void setPayment(double p){
        _payment = p;
        freshen(Value.principal);
        compute();
    }
    
    public void setLoanLength(double len){
        _length = len;
        freshen(Value.length);
        compute();
    }
    
    public void setFrequency(Frequency f){
        _freq = f;
        compute();
    }
    
}
