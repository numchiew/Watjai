'use strict';

const Boom = require('boom');
const uuid = require('node-uuid');
const Joi = require('joi');

exports.register = function (server, options, next) {

    const db = server.app.db;

    var samplingFrequency = 160;
    var WIND = 1000;
    var WINDOW = 100;
    var TAVWINDOW = 80;
    var THRESHOLD = 0.9;
    var LOW_THRESHOLD = 0.161;
    var REFACTORY = 0.2;
    var sampleNo = 0;
    var t_refact = 0;
    var low_threshold = LOW_THRESHOLD;
    var NO_LP_COEFF = 38;
    var NO_HP_COEFF = 312;
    var l_p_filt_coeff = [-0.00009346235941,
        -0.00040034167381,
        -0.00111158024526,
        -0.00243326225590,
        -0.00450640152123,
        -0.00729365122836,
        -0.01046324024778,
        -0.01331254752728,
        -0.01477621421105,
        -0.01355002231955,
        -0.00833248504761,
         0.00185207850745,
         0.01732068871587,
         0.03748060637556,
         0.06074599598689,
         0.08467505972049,
         0.10632605270596,
         0.12276996113233,
         0.13164895219470,
         0.13164895219470,
         0.12276996113233,
         0.10632605270596,
         0.08467505972049,
         0.06074599598689,
         0.03748060637556,
         0.01732068871587,
         0.00185207850745,
        -0.00833248504761,
        -0.01355002231955,
        -0.01477621421105,
        -0.01331254752728,
        -0.01046324024778,
        -0.00729365122836,
        -0.00450640152123,
        -0.00243326225590,
        -0.00111158024526,
        -0.00040034167381,
        -0.00009346235941];
    var h_p_filt_coeff = [0.00329398890514,
        -0.00028439114267,
        -0.00027900767416,
        -0.00027821248664,
        -0.00028095530856,
        -0.00028727959281,
        -0.00029610732188,
        -0.00030743716597,
        -0.00032012741679,
        -0.00033409688125,
        -0.00034816121452,
        -0.00036231845336,
        -0.00037542569706,
        -0.00038742259197,
        -0.00039710281868,
        -0.00040454224477,
        -0.00040860846756,
        -0.00040937214382,
        -0.00040568775819,
        -0.00039799975327,
        -0.00038499276946,
        -0.00036735004266,
        -0.00034380162937,
        -0.00031534711747,
        -0.00028061710510,
        -0.00024097275304,
        -0.00019502307100,
        -0.00014466724385,
        -0.00008815768937,
        -0.00002818561032,
         0.00003778137254,
         0.00010508534223,
         0.00017874793479,
         0.00025562229835,
         0.00032460518936,
         0.00040513655233,
         0.00048306988452,
         0.00056103453189,
         0.00063481489377,
         0.00070631466823,
         0.00077269630528,
         0.00083498376038,
         0.00089068215672,
         0.00094020255813,
         0.00098117981153,
         0.00101379580497,
         0.00103609682806,
         0.00104822948731,
         0.00104840235022,
         0.00103682499562,
         0.00101225243592,
         0.00097488722045,
         0.00092374034938,
         0.00085948442595,
         0.00078130672239,
         0.00069020311185,
         0.00058563383852,
         0.00046903179604,
         0.00034010001721,
         0.00020063055966,
         0.00005057944525,
        -0.00010769767645,
        -0.00027419618199,
        -0.00044587722659,
        -0.00062330137332,
        -0.00080164262228,
        -0.00098236045617,
        -0.00116264276831,
        -0.00133737610903,
        -0.00150803957428,
        -0.00167135430060,
        -0.00182564086747,
        -0.00196774835135,
        -0.00209592909364,
        -0.00220767423805,
        -0.00230140994360,
        -0.00237503145684,
        -0.00242706963862,
        -0.00245555648141,
        -0.00245906979924,
        -0.00243611677704,
        -0.00238583751270,
        -0.00230710918363,
        -0.00219932736644,
        -0.00206235594730,
        -0.00189595603396,
        -0.00170051413018,
        -0.00147659772987,
        -0.00122514375256,
        -0.00094741201395,
        -0.00064488628873,
        -0.00031957582626,
         0.00002641609610,
         0.00039043639778,
         0.00076985614809,
         0.00116125302498,
         0.00156158742029,
         0.00196649819842,
         0.00237341023967,
         0.00277629813580,
         0.00317244055478,
         0.00355754264775,
         0.00392575388895,
         0.00427312882178,
         0.00459491577104,
         0.00488692966214,
         0.00514416988773,
         0.00536231492610,
         0.00553659307785,
         0.00566302072149,
         0.00573736583063,
         0.00575629365108,
         0.00571612526400,
         0.00561385859726,
         0.00544627623023,
         0.00521152654179,
         0.00490725730326,
         0.00453199267692,
         0.00408487648568,
         0.00356524980028,
         0.00297319953436,
         0.00230922276973,
         0.00157444401596,
         0.00077049840301,
        -0.00010050086507,
        -0.00103581044612,
        -0.00203221516402,
        -0.00308592023264,
        -0.00419275109899,
        -0.00534786615004,
        -0.00654625738906,
        -0.00778170658540,
        -0.00904933366708,
        -0.01034139380427,
        -0.01165204179707,
        -0.01297445451386,
        -0.01430133695905,
        -0.01562510885414,
        -0.01693846937362,
        -0.01823399649977,
        -0.01950435726868,
        -0.02074211574665,
        -0.02193997693588,
        -0.02309067176107,
        -0.02418717538846,
        -0.02522291117292,
        -0.02619175143339,
        -0.02708783951403,
        -0.02790503237395,
        -0.02863884069299,
        -0.02928443258279,
        -0.02983758031215,
        -0.03029512740559,
        -0.03065389292862,
        -0.03106724037372,
         0.96888079336632,
        -0.03106724037372,
        -0.03091190298954,
        -0.03065389292862,
        -0.03029512740559,
        -0.02983758031215,
        -0.02928443258279,
        -0.02863884069299,
        -0.02790503237395,
        -0.02708783951403,
        -0.02619175143339,
        -0.02522291117292,
        -0.02418717538846,
        -0.02309067176107,
        -0.02193997693588,
        -0.02074211574665,
        -0.01950435726868,
        -0.01823399649977,
        -0.01693846937362,
        -0.01562510885414,
        -0.01430133695905,
        -0.01297445451386,
        -0.01165204179707,
        -0.01034139380427,
        -0.00904933366708,
        -0.00778170658540,
        -0.00654625738906,
        -0.00534786615004,
        -0.00419275109899,
        -0.00308592023264,
        -0.00203221516402,
        -0.00103581044612,
        -0.00010050086507,
         0.00077049840301,
         0.00157444401596,
         0.00230922276973,
         0.00297319953436,
         0.00356524980028,
         0.00408487648568,
         0.00453199267692,
         0.00490725730326,
         0.00521152654179,
         0.00544627623023,
         0.00561385859726,
         0.00571612526400,
         0.00575629365108,
         0.00573736583063,
         0.00566302072149,
         0.00553659307785,
         0.00536231492610,
         0.00514416988773,
         0.00488692966214,
         0.00459491577104,
         0.00427312882178,
         0.00392575388895,
         0.00355754264775,
         0.00317244055478,
         0.00277629813580,
         0.00237341023967,
         0.00196649819842,
         0.00156158742029,
         0.00116125302498,
         0.00076985614809,
         0.00039043639778,
         0.00002641609610,
        -0.00031957582626,
        -0.00064488628873,
        -0.00094741201395,
        -0.00122514375256,
        -0.00147659772987,
        -0.00170051413018,
        -0.00189595603396,
        -0.00206235594730,
        -0.00219932736644,
        -0.00230710918363,
        -0.00238583751270,
        -0.00243611677704,
        -0.00245906979924,
        -0.00245555648141,
        -0.00242706963862,
        -0.00237503145684,
        -0.00230140994360,
        -0.00220767423805,
        -0.00209592909364,
        -0.00196774835135,
        -0.00182564086747,
        -0.00167135430060,
        -0.00150803957428,
        -0.00133737610903,
        -0.00116264276831,
        -0.00098236045617,
        -0.00080164262228,
        -0.00062330137332,
        -0.00044587722659,
        -0.00027419618199,
        -0.00010769767645,
         0.00005057944525,
         0.00020063055966,
         0.00034010001721,
         0.00046903179604,
         0.00058563383852,
         0.00069020311185,
         0.00078130672239,
         0.00085948442595,
         0.00092374034938,
         0.00097488722045,
         0.00101225243592,
         0.00103682499562,
         0.00104840235022,
         0.00104822948731,
         0.00103609682806,
         0.00101379580497,
         0.00098117981153,
         0.00094020255813,
         0.00089068215672,
         0.00083498376038,
         0.00077269630528,
         0.00070631466823,
         0.00063481489377,
         0.00056103453189,
         0.00048306988452,
         0.00040513655233,
         0.00032460518936,
         0.00025562229835,
         0.00017874793479,
         0.00010508534223,
         0.00003778137254,
        -0.00002818561032,
        -0.00008815768937,
        -0.00014466724385,
        -0.00019502307100,
        -0.00024097275304,
        -0.00028061710510,
        -0.00031534711747,
        -0.00034380162937,
        -0.00036735004266,
        -0.00038499276946,
        -0.00039799975327,
        -0.00040568775819,
        -0.00040937214382,
        -0.00040860846756,
        -0.00040454224477,
        -0.00039710281868,
        -0.00038742259197,
        -0.00037542569706,
        -0.00036231845336,
        -0.00034816121452,
        -0.00033409688125,
        -0.00032012741679,
        -0.00030743716597,
        -0.00029610732188,
        -0.00028727959281,
        -0.00028095530856,
        -0.00027821248664,
        -0.00027900767416,
        -0.00028439114267,
         0.00329398890514];
    var bp_delays = 172;
    var total_delays = 208;
    var tav_delay = 32;
    var slp_delay = 4;
    var  qrswin = [];
	var  data= [];
	var  time_buff = [];	
	var  new_l_p_filt = [];
	var  new_h_p_filt=[];
	var  slope=[];
	var  square=[];
	var  time_av=[];
	
	var five_pt_median_array = [];
	var five_pt_median_value=0.2;
	var window_max = 0.2;
	
    var no_of_fids=0;
    var mult = 4;
    var check2 = 0;
    var check1= mult*samplingFrequency, check2;

    var fid_pt_pandt_time=0;
    var last_fid_time=0;
    var last_fid=0;
    var rr_int;
    var heart_rate;
    var abnormal_status = true;
    var abnormal_detail = "";

    function initialise(){
        var qrswin = [];
        var data = [];
        var time_buff = [];
        var new_l_p_filt = [];
        var new_h_p_filt = [];
        var slop = [];
        var square = [];

        var time_av = [];
        var five_pt_median_array = [];

        //re-init
         sampleNo=0;
        t_refact = 0;
        low_threshold = LOW_THRESHOLD;
        five_pt_median_value = 0.2;
        window_max = 0.2;
        no_of_fids = 0;
        check2= 0;
        fid_pt_pandt_time = 0;
        last_fid = 0;
        rr_int = 0;
        heart_rate = 0;

        var indexInitialise = 0;

        for(indexInitialise=0;indexInitialise<WIND;indexInitialise++){
            qrswin[indexInitialise] = 0;
            time_buff[indexInitialise] = 0;
            new_l_p_filt[indexInitialise] = 0;
            new_h_p_filt[indexInitialise] = 0;
        }
        for(indexInitialise=0;indexInitialise<WINDOW;indexInitialise++){
            data[indexInitialise]=0;
			slope[indexInitialise]=0;
			square[indexInitialise]=0;
        }
        for (indexInitialise=0;indexInitialise<TAVWINDOW;indexInitialise++){
			time_av[indexInitialise]=0;
        }
        for (indexInitialise=0;indexInitialise<5;indexInitialise++){
			five_pt_median_array[indexInitialise]=1;
		}
    }

    function scan_back(right_window,left_window){
        var  fiducial_index, S_index;
        var fiducial_time=0, fiducial_height, S_height;
        var hi_check = -1.0;
        var lo_check = 100000000.0;

        right_window = right_window - (tav_delay+slp_delay) -bp_delays;
        left_window  = left_window  - (tav_delay+slp_delay) -bp_delays;

        fiducial_index = right_window;

        // right window is closing back to the left one through each loop 
        while ((right_window - left_window)  >= 0)
        {
            if(qrswin[(right_window)%WIND] >= hi_check)
            {
                hi_check = qrswin[(right_window)%WIND];
                fiducial_index = right_window;
                fiducial_height = hi_check;
                fiducial_time   = time_buff[(fiducial_index)%WIND];
            }
            //  also ... If the previous value is higher than any of the data in
            // this window set the fiducial point to be that datum ...
            // avoids local maxima
            // if (data[(right_window-1)%WINDOW] > data[(fiducial)%WINDOW]) 
            if(qrswin[(right_window)%WIND] <= lo_check)
            {
                lo_check = qrswin[(right_window)%WIND];
                S_index = right_window;
                S_height = lo_check;
            }
            // back track through the window to test each data point 
            right_window--;
        }
        if (fiducial_index + tav_delay + slp_delay + bp_delays-t_refact<REFACTORY){
            fiducial_index=-1;
        }

        return fiducial_index;
    }

    function locate_R(){
        var left_margin=0, right_margin=0, x;
        var fiducial_pt = 0;
        if (sampleNo<=t_refact)
	    {
	        fiducial_pt=-1;
	        return(fiducial_pt);
        }
        
        if ((time_av[(sampleNo-2)%TAVWINDOW] < time_av[(sampleNo-1)%TAVWINDOW])
        && (time_av[(sampleNo-1)%TAVWINDOW] > time_av[(sampleNo%TAVWINDOW)])
        && (time_av[(sampleNo-1)%TAVWINDOW]>(low_threshold*five_pt_median_value)))
        {
             // if this is true, set the WINDOW sized window's maximum to be this 
	        window_max = time_av[(sampleNo-1)%TAVWINDOW];
        }
        //if the data falls below the 90% threshold (the last data point is
            //	     above it and the next below) whilst in this region 
                    if ( (time_av[((sampleNo-1)%TAVWINDOW)] >= (0.9*window_max))
                        && (time_av[(sampleNo%TAVWINDOW)] < (0.9*window_max))
                        && (time_av[((sampleNo-1)%TAVWINDOW)] > (low_threshold*five_pt_median_value)) )
                    {
                        right_margin = (sampleNo-1);
                        x = right_margin;
                        
                        
                        while (left_margin == 0)
                        {
            //	             starting from the peak, if the current value is greater
            //	             than the threshold AND the previous value then this is the
            //	             left margin of the window, otherwise scan back 
                            if((time_av[(x%TAVWINDOW)] >= (0.9*window_max)) && (time_av[((x-1)%TAVWINDOW)] < (0.9*window_max)))
                            {
                                left_margin = x;
            //	                fidicial point is half way between the two points
            //	                (minus the delays =40 plus rounding error) 
            //	                fiducial_pt = ((right_margin+left_margin)/2)-39; 
                                
                                // look back through original data to find the true peak 
                                // removing delays (was 40 for 128Hz now 56  .. 193 new_filt?
                                
                                
                                // look back through original data to find the true peak 
                                fiducial_pt = scan_back(right_margin, left_margin);
            
                                //Test JOs in scan_back one can return -1 if detection within refactory period
                                if (fiducial_pt>0)
                                {
                                
                                // pass the number fiducial points encountered so far,
                                // and the largest value in this region into the five
                                // point median filter to reset the threshold */
                                    five_pt_median_value=five_pt_median_funct(window_max,no_of_fids);
                                    no_of_fids++;
                                }
                            }
                            else x--;
                        }
                        //******** call original data scan back function here ************/
                        left_margin = 0;
                    }
                    return fiducial_pt;
    }

    function five_pt_median_funct(new_value, index_5_pt){
        var i,j;
        var noswap=5;
        var median=0, temp_median = [], dummy;
        
        j = index_5_pt%5;
        five_pt_median_array[j] = new_value;
        temp_median[j] = five_pt_median_array[j]
        // put each value of the five point median array into a temporary array
	    // for sorting, so we do not keep very old values 
	    for (i=0; i<=4; i++)
	    {
	        temp_median[i] = five_pt_median_array[i];
        }
        // while the order is still being swapped, continue. 
	    while (noswap > 0)
	    {
	        
	        // scan up the array once swapping adjacent values if the one
	        // above is larger 
	        
	        noswap = 0; // initialise the number of no exchanges 
	        
	        for (i=0; i<=3; i++)
	        {
	            // if the one above is greater, then shuffle it up one -
	            // repeat the process for each neighbouring pair 
	            if (temp_median[i+1]<temp_median[i])
	            {
	                dummy = temp_median[i];
	                temp_median[i] = temp_median[i+1];
	                temp_median[i+1] = dummy;
	                noswap++;
	            }
	        }
	        median = temp_median[2];
	        
	    }
        

	    return median
    }

    function seed_5pt_median_filt(seed){
        var i;
        for(i=0;i<5;i++){
            five_pt_median_array[i] = seed;
        }
    }

    function hpsort(n,ra){
        var highest = 0,lowest = 0;
        var med_filt = 0;
        var threshold = 0,thresh = 0;

        ra.sort();
        
        /* work out the median value of the 80-85 percentile */
        highest=ra[parseInt(TAVWINDOW*0.85)];
        
		lowest=ra[parseInt(TAVWINDOW*0.8)];
        med_filt=(highest+lowest)/2.0;
        
        threshold=ra[parseInt(TAVWINDOW*0.1)]; // noise floor

        thresh=(med_filt+threshold)/2.0;    //
	    
		low_threshold = thresh/med_filt;

	    //test JOs
	    if (low_threshold>LOW_THRESHOLD)
	    {
	        low_threshold=LOW_THRESHOLD;
	    }
		
		return med_filt;
    }
   
    function reset_5pt_median_filt(){
        var median_seed;
        var dummy = [];
	    

	    // make a copy of the array 
       // copy.arraycopy(time_av, 0, dummy, 0, time_av.length);
       dummy = time_av.slice();
       

	    // and sort it in ascending ranked order
	    median_seed = hpsort(TAVWINDOW, dummy);
	    
	    // Note - this also finds largest 80-85% values */
	    // pick middle value to seed 5 point median filter .. look - */
	    
	    seed_5pt_median_filt(median_seed);
    }

    function time_average(){
        var accum = 0;
        var i, BUFF_SZ;
        BUFF_SZ = samplingFrequency/4;

        for(i=0; i<=BUFF_SZ-1; i++)
	    {
	    	if ((sampleNo-i)>0){
		        accum += square[parseInt((sampleNo-i)%WINDOW)];
	    	}
        }
        
         // 64 point window causing further 32 point delay 
        time_av[parseInt(sampleNo%TAVWINDOW)]  = accum/(BUFF_SZ);
    }

    function squaring_func(){
        square[parseInt(sampleNo%WINDOW)] = slope[parseInt(sampleNo%WINDOW)]*slope[parseInt(sampleNo%WINDOW)]*100;
    }

    function slope_func(){
        if ((sampleNo-4)>0){
			slope[parseInt(sampleNo%WINDOW)]=((2*new_h_p_filt[parseInt(sampleNo%WIND)])+new_h_p_filt[parseInt((sampleNo-1)%WIND)]-new_h_p_filt[parseInt((sampleNo-3)%WIND)]-(2*new_h_p_filt[parseInt((sampleNo-4)%WIND)]))/8;
		}
    }

    function new_high_pass_filter(){
        var i;
        new_h_p_filt[parseInt(sampleNo%WIND)] = 0.0;
        for (i=0; i<=NO_HP_COEFF-1; i++) //
        {
            if ((sampleNo-i)<0){
                new_h_p_filt[parseInt(sampleNo%WIND)] = 0;
            }
            else{
                new_h_p_filt[parseInt(sampleNo%WIND)] += new_l_p_filt[parseInt((sampleNo-i)%WIND)]*h_p_filt_coeff[i];
            }
                
        }
    }

    function new_low_pass_filter(){
        var i;
        new_l_p_filt[parseInt(sampleNo%WIND)] =0.0;
        
        for (i=0; i<=NO_LP_COEFF-1; i++)
        {        
            
            if ((sampleNo-i)<0) {
                new_l_p_filt[parseInt(sampleNo%WIND)] = 0;
            }
            else
            {
                new_l_p_filt[parseInt(sampleNo%WIND)] += qrswin[parseInt((sampleNo-i)%WIND)]*l_p_filt_coeff[i];
            }
        }	    
    }

    function filter_algorithms(window_index, window_indextav){
        var fid_pt_index = 0 ;
		var dataIndexcnv,i;
    
		var rel_time=time_buff[(sampleNo%WIND)];
		var rel_time_bp=-1;
		var rel_time_tav=-1;
		if ((sampleNo-bp_delays)>=0){
			rel_time_bp=time_buff[(sampleNo-bp_delays)%WIND];
		}
		var filter_delays = 209.0/256.0;
    
		fid_pt_index=0;
    
		dataIndexcnv =  sampleNo%WIND;
    
		// 37 point window & therefore a delay of 18 
		new_low_pass_filter();
        
		// 313 point window & therefore a delay of 156
        new_high_pass_filter();
        /* 4 point window & therefore a delay of 2 */
        slope_func();
    
        
        //  instantaneous therefore no delay 
        squaring_func();
    
    
        /* 64 point window & therefore a delay of 32 */
        time_average();

        /* when we have 1000+ non-zero entries in the integrated filter */
        if(sampleNo==(total_delays+WIND))
        {
            reset_5pt_median_filt();
        }
        
        if(sampleNo > WIND+total_delays){
            fid_pt_index = locate_R();
        }
            return fid_pt_index; // fid_pt as found in bandpassed data	
    }

    function DetectQRS(x,y){
        var fid = 0;
        var dataIndex,dataIndextav,dataIndexcnv;
        dataIndextav =  sampleNo %  TAVWINDOW;
	    dataIndex =  sampleNo %  WINDOW;
	    dataIndexcnv =  sampleNo %  WIND;
	    data[dataIndex] = y; 
        qrswin[dataIndexcnv] = y;
        time_buff[(sampleNo%WIND)] = x;
        
                fid = filter_algorithms(dataIndex, dataIndextav);
                
                
                return fid;
    }
    
    function get_result(data){
        var rrAvg = [];
        var countBeat = 0;
        var  rr;
        var  rrLow;
        var  rrHigh;
        var  rrMissed;
        var recentBeat = [];
        var count = 0;
        var nOfBeat = 0;
        var unnormal_state_count = 0;
        var result_rate = 0; // for return percent of Arrthymia.
        var temp = 0;
        var fid_pandt = 0;
        var time = 0;
        while(temp < data.length){

            var sample = data[temp];
            fid_pandt = DetectQRS(time,sample);
            //console.log(fid_pandt);
            temp++;

            check2 = (sampleNo-last_fid) % (check1);
            if((check2 == check1- 1) && (sampleNo/samplingFrequency > 8)){
                low_threshold = low_threshold*0.9;
            }
    
            if(fid_pandt > 0){
                fid_pt_pandt_time = time_buff[(fid_pandt)%WIND];
                rr_int = fid_pt_pandt_time-last_fid_time;
                if (rr_int > 0) {
                    heart_rate = 60.0/rr_int;
                }
                
                //collect 8 most recent Beat.
                rrAvg[countBeat] = rr_int*1000;
                countBeat++;
                if(countBeat == 7){
                    countBeat = 0;
                }
    
                var sumR = 0; //Average RR
                for(var l =0;l<rrAvg.length;l++){
                    sumR += rrAvg[l];
                }
                sumR = sumR * 0.125; //set new RR AVG.
                var rr = rr_int*1000;
    
                // set Low-High limith to determine normal/unnormal arrthymia.
                rrLow = 0.93 * sumR; 
                rrHigh = 1.16 * sumR;
                rrMissed = 1.66 * sumR;
    
                if(rr >= rrLow && rr <= rrHigh){
                    recentBeat[count] = true;
                 }else if(rr < rrLow || rr > rrHigh){
                     recentBeat[count] = false;
                 }else{
                     break;
                 }
                 count++;
    
                 if(nOfBeat<100){
                    nOfBeat++;
                }
                if(count == 99){
                    count = 0; //reset recentBeat to overwrite data in recent beat.
                }
                for(var j = 0;j < nOfBeat;j++){
                    if(recentBeat[j] == false){
                        unnormal_state_count++;
                    }
                }
                if(nOfBeat>8){
                result_rate = (unnormal_state_count / nOfBeat)*100.0;
                }
                console.log("unnormal_state_count : " + unnormal_state_count + "\nnOfBeat : " + nOfBeat +"\nRisk Rate : " + result_rate+"\nHeart Rate : " + heart_rate);
                if(result_rate > 95 && nOfBeat > 8){
                    
                    if(heart_rate < 60){
                        abnormal_status = false;
                        abnormal_detail = "Bradycardia";
                        console.log("case1");
                        break;
                    }
                    else if(heart_rate > 100){
                        abnormal_status = false;
                        abnormal_detail = "Tachycardia";
                        console.log("case2");
                        break;
                    }else{
                        abnormal_status = false;
                        abnormal_detail = "Normal sinus";
                        console.log("case3");
                        break;
                    }
                }
                
                unnormal_state_count = 0;
                if(rr_int > REFACTORY)
                {
                    if(heart_rate<30.0){
                        heart_rate=30.0;
                    }
                    if(heart_rate>200.0)
                    {
                        heart_rate=200.0;
                        reset_5pt_median_filt();
                    }	                
                }else{
                    if(heart_rate<30.0){
                        heart_rate=30.0;
                    }
                    if(heart_rate>200.0)
                    {
                        heart_rate=200.0;
                        
                    }
                }
    
                last_fid=fid_pandt;
                last_fid_time = fid_pt_pandt_time;
    
            }
            time += 1/ samplingFrequency;
			sampleNo++ ;
        }
        
        return no_of_fids;
    }
    
    // create Wat jai Measure Alert
    server.route({
        method: 'POST',
        path: '/watjaimeasure',
        handler: function (request, reply) {
            var number, genId, checkYear, checkMonth, checkDay;
            var year, month, day;
            var getDate;
            getDate = new Date(Date.now());
            getDate.setUTCHours(getDate.getUTCHours() + 7);
            getDate = getDate.toISOString();
            getDate = getDate.substr(2, 8);
            year = getDate.substr(0, 2); 
            month = getDate.substr(3, 2);
            day = getDate.substr(6, 2);
            db.WatjaiMeasure.find({}, { measuringId: 1, _id: 0 }).sort({ measuringId: -1 }).limit(1, (err, result) => {
                if (err) {
                    return reply(Boom.wrap(err, 'Internal MongoDB error'));
                }
                const tmp = result;
                if (tmp[0] != null) {
                    var getId = tmp[0].measuringId + "";
                    var getNumber = getId.substr(2, 11);
                    checkYear = getId.substr(2, 2);
                    checkMonth = getId.substr(4, 2);
                    checkDay = getId.substr(6, 2);
                    if (year == checkYear && month == checkMonth && day == checkDay) {
                        number = parseInt(getNumber);
                        number = number + 1;
                        genId = "ME" + number;
                    } else {
                        genId = "ME" + year + month + day + "00001";
                    }
                } else {
                    genId = "ME" + year + month + day + "00001";
                }
                const measuring = request.payload;
                var date = new Date(Date.now());
                date.setUTCHours(date.getUTCHours() + 7);
                measuring.alertTime = date;
                measuring.measuringId = genId;
                var measuringData = request.payload.measuringData;
                initialise();
                var detecting = get_result(measuringData);
                measuring.heartRate = parseInt(heart_rate);
                
                if (abnormal_status == false) {
                    measuring.abnormalStatus = abnormal_status;
                    measuring.abnormalDetail = abnormal_detail;
                }
                db.WatjaiMeasure.save(measuring, (err, result) => {

                    if (err) {
                        return reply(Boom.wrap(err, 'Internal MongoDB error'));
                    }
                });

                reply({"status": true});
            })
        },
        config: {
            validate: {
                payload: {
                    measuringData: Joi.array().min(1).required(),
                    patId: Joi.string().min(9).max(9).required()
                }
            }
        }
    });
    

    return next();
};

exports.register.attributes = {
    name: 'routes-detectheartfailure'
};