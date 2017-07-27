
var userAgent = navigator.userAgent;
  
jQuery.extend({
		
		IEVersion:function()
		{
		           var reIE = new RegExp("MSIE (\\d+\\.\\d+);");  
		           reIE.test(userAgent);  
		           var fIEVersion = parseFloat(RegExp["$1"]);  
		         
		},
	   createUploadIframe: function(id, uri)
	   {
	     //create frame
	              var frameId = 'jUploadFrame' + id;
	              
	              if(window.ActiveXObject) {
	            	//  alert("IE...");
	                  //var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
	            	  var v= jQuery.IEVersion();
	            	  console.log("IE version:" + v);
	                  if(v>=9.0)
	                  {
	                      var io = document.createElement('iframe');  
	                      io.id = frameId;  
	                      io.name = frameId;  
	                  }else 
	                  {
	                      var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');  
	                      if(typeof uri== 'boolean'){
	                          io.src = 'javascript:false';
	                      }
	                      else if(typeof uri== 'string'){
	                          io.src = uri;
	                      }
	                  }
	              }else {
	                  var io = document.createElement('iframe');
	                  io.id = frameId;
	                  io.name = frameId;
	              }
	              io.style.position = 'absolute';
	              io.style.top = '-1000px';
	              io.style.left = '-1000px';

	              document.body.appendChild(io);

	              return io;   
	      },
    createUploadForm: function(id, fileElementId, data)
	{
		//create form	
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');	
		if(data)
		{
			for(var i in data)
			{
				jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
			}			
		}		
		var oldElement = jQuery('#' + fileElementId);
		oldElement.unbind("change");
		
		var newElement = jQuery(oldElement).clone();
		jQuery(oldElement).attr('id', fileId);
		jQuery(oldElement).before(newElement);
		jQuery(oldElement).appendTo(form);
				
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');
		return form;
    },

    ajaxFileUpload: function(s) {
    	
    	 var filename = $('#'+s.fileElementId).val();
    	 console.log("filename=="+filename);
    	 
    	 var ext = filename.split('.').pop().toLowerCase();
    	if(filename ==null || ""== filename)
    	{
    		 return ;
    	}
    	 
    //	 console.log("ext:" + ext);
    	 if(s.valid_extensions !=null && $.inArray(ext, s.valid_extensions) == -1)
    	 {
    		  if ( s.error )
                  s.error(null, "无效的文件格式,只支持:" +s.valid_extensions.join(', '));
    		 return ;
    	 }
    	
    	
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = s.fileElementId;
        console.log("jUploadFrame"+id+":"+$("#jUploadFrame" + id));
        console.log("jUploadForm"+id+":"+$("#jUploadForm" + id));
        
        $("#jUploadFrame" + id).remove();
        $("#jUploadForm" + id).remove();
        
		var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;		
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
		{
			jQuery.event.trigger( "ajaxStart" );
		}            
        
        var requestDone = false;
        // Create the request object
        var xml = {};
        if ( s.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
        // Wait for a response to come back
        var uploadCallback = function(isTimeout)
		{			
			var io = document.getElementById(frameId);
            try 
			{				
				if(io.contentWindow)
				{
					 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
					 
				}else if(io.contentDocument)
				{
					 xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}						
            }catch(e)
			{
				jQuery.handleError(s, xml, null, e);
			}
            if ( xml || isTimeout == "timeout") 
			{				
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
					{
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );    
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success )
                            s.success( data, status );
    
                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        jQuery.handleError(s, xml, status);
                } catch(e) 
				{
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(io).unbind()

                setTimeout(function()
									{	try 
										{
											jQuery(io).remove();
											jQuery(form).remove();	
											
										} catch(e) 
										{
											jQuery.handleError(s, xml, null, e);
										}									

									}, 100)

                xml = null

            }
        }
        // Timeout checker
        if ( s.timeout > 0 ) 
		{
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try 
		{

			var form = jQuery('#' + formId);
			jQuery(form).attr('action', s.url);
			jQuery(form).attr('method', 'POST');
			jQuery(form).attr('target', frameId);
            if(form.encoding)
			{
				jQuery(form).attr('encoding', 'multipart/form-data');      			
            }
            else
			{	
				jQuery(form).attr('enctype', 'multipart/form-data');			
            }			
            jQuery(form).submit();

        } catch(e) 
		{			
            jQuery.handleError(s, xml, null, e);
        }
		
		jQuery('#' + frameId).load(uploadCallback	);
        return {abort: function () {}};	

    },
    
    handleError: function( s, xhr, status, e ) 		{
    	// If a local callback was specified, fire it
    			if ( s.error ) {
    				s.error.call( s.context || s, xhr, status, e );
    			}

    			// Fire the global callback
    			if ( s.global ) {
    				(s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );
    			}
	},
    uploadHttpData: function( r, type ) {
    	//alert(r.responseText+":==="+ r.responseXML);
//    	var str ="";
//    	for(var s in r)
//    	 str += s+" " ;
//    	
    	//alert(str);
    	
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
      //  alert(r.responseXML +"-----"+ r.responseText);
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json")
        	//data = jQuery.parseJSON(jQuery(data).text());
            eval( "data = " + data );
        // evaluate scripts within html
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();

        return data;
    }
})

