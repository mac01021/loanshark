/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quartyard.loanshark;


public enum TimeUnit {
    Days(1), Weeks(7), Months(30.5), Years(365.25);
    
    double _nbDays;
    
    TimeUnit(double nbDays){
        _nbDays = nbDays;
    }
}

enum Frequency {
    Daily(TimeUnit.Days), 
    Weekly(TimeUnit.Weeks), 
    Monthly(TimeUnit.Months), 
    Yearly(TimeUnit.Years);
    
    TimeUnit _unit;
    
    Frequency(TimeUnit u){
        _unit = u;
    }
}
