(function($){

  // a case insensitive jQuery :contains selector
  $.expr[":"].searchableSelectContains = $.expr.createPseudo(function(arg) {
    return function( elem ) {
      return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
    };
  });

  $.searchableSelect = function(element, options) {
    this.element = element;
    this.options = options || {};
   // console.log("option:"+options.data+"----");
    
    this.init();

    var _this = this;

    this.searchableElement.click(function(event){
      // event.stopPropagation();
    	//console.log("searchableElement.click");
       _this.show();
    }).on('keydown', function(event){
      if (event.which === 13 || event.which === 40 || event.which == 38){
        event.preventDefault();
        _this.show();
      }
    });

    $(document).on('click', null, function(event){
      if(_this.searchableElement.has($(event.target)).length === 0)
        _this.hide();
    });
    
	  
    this.input.on('keydown', function(event){
      event.stopPropagation();
      if(event.which === 13){         //enter
        event.preventDefault();
        _this.selectCurrentHoverItem();
        _this.hide();
      } else if (event.which == 27) { //ese
        _this.hide();
      } else if (event.which == 40) { //down
        _this.hoverNextItem();
      } else if (event.which == 38) { //up
        _this.hoverPreviousItem();
      }
    }).on('keyup', function(event){
      if(event.which != 13 && event.which != 27 && event.which != 38 && event.which != 40)
        _this.filter();
    })
  }

  var $sS = $.searchableSelect;

  $sS.fn = $sS.prototype = {
    version: '0.0.1'
  };

  $sS.fn.extend = $sS.extend = $.extend;

  $sS.fn.extend({
	  
	  
    init: function(){
      var _this = this;
      this.element.hide();
    //  console.log("this.options.listHeight::"+this.options.listHeight);
      if(this.options.listHeight)
    	  this.searchableElement = $('<div tabindex="0" class="searchable-select" style="width:'+this.options.listHeight+'"></div>');
      else
    	  this.searchableElement = $('<div tabindex="0" class="searchable-select"></div>');
      
    	    	
      if(this.options.listHeight)
    	  this.holder = $('<div class="searchable-select-holder" style="width:'+this.options.listHeight+'"></div>');
      else
    	  this.holder = $('<div class="searchable-select-holder"></div>');
      
      this.dropdown = $('<div class="searchable-select-dropdown searchable-select-hide"></div>');
      this.input = $('<input type="text" class="searchable-select-input" />');
      this.items = $('<div class="searchable-select-items"></div>');
      this.caret = $('<span class="searchable-select-caret"></span>');
	  this.close_icon = $('<span class="searchable-select-close" style="display:none">╳</span>');
      
      this.scrollPart = $('<div class="searchable-scroll"></div>');
      this.hasPrivious = $('<div class="searchable-has-privious">...</div>');
      this.hasNext = $('<div class="searchable-has-next">...</div>');

      this.setValue=function(sid, stext)
      {
    	  //this.initSelect(sid,stext);
    	  //create new item , and selected it 
    	   	  var datalist = this.items.find('.searchable-select-item[data-value='+sid+']');
    	      if(datalist && datalist.length >0)
    	    	{
    	    	  _this.selectItem(datalist.eq(0));
    	    	  //	datalist.removeClass('searchable-select-hide');
    	    	}
    	      else
    	    	  {
    	    	  	//列表中不存在, 添加
		    	  
		    	     var item = $('<div class="searchable-select-item" data-value="'+sid+'">'+stext+'</div>');
		 	        item.on('mouseenter', function(){
			          $(this).addClass('hover');
			        }).on('mouseleave', function(){
			          $(this).removeClass('hover');
			        }).click(function(event){
			          event.stopPropagation();
			          _this.selectItem($(this));
			          _this.hide();
			        });
			        _this.items.prepend(item);
			        _this.items.scrollTop(0);
			        this.selectItem(item);
    	    	  }
    	      
	  };
	  this.close_icon.on('click',function(event){
		  event.stopPropagation();
		 // console.log("placeholder:"+_this.options.placeholder);
		  if(_this.options.placeholder)
			  _this.initSelect('',_this.options.placeholder);
		  else
			  _this.initSelect('','');
			  
	  });
      this.hasNext.on('mouseenter', function(){
        _this.hasNextTimer = null;

        var f = function(){
          var scrollTop = _this.items.scrollTop();
          _this.items.scrollTop(scrollTop + 20);
          _this.hasNextTimer = setTimeout(f, 50);
        }

        f();
      }).on('mouseleave', function(event) {
        clearTimeout(_this.hasNextTimer);
      });

      this.hasPrivious.on('mouseenter', function(){
        _this.hasPriviousTimer = null;

        var f = function(){
          var scrollTop = _this.items.scrollTop();
          _this.items.scrollTop(scrollTop - 20);
          _this.hasPriviousTimer = setTimeout(f, 50);
        }

        f();
      }).on('mouseleave', function(event) {
        clearTimeout(_this.hasPriviousTimer);
      });

      this.dropdown.append(this.input);
      this.dropdown.append(this.scrollPart);

      this.scrollPart.append(this.hasPrivious);
      this.scrollPart.append(this.items);
      this.scrollPart.append(this.hasNext);

      this.searchableElement.append(this.caret);
	  this.searchableElement.append(this.close_icon);
     // this.searchableElement.append(this.caret_delete);
      
      
      
      this.searchableElement.append(this.holder);
      this.searchableElement.append(this.dropdown);
      this.element.after(this.searchableElement);

      this.buildItems();
      this.setPriviousAndNextVisibility();
      //==================== 默认选中的数值, 方便修改====
     // console.log("------------------"+this.options.selectid);
      if(this.options.selectid && this.options.selecttext  && this.options.selectid !=null  &&  this.options.selectid!='')
    	  this.initSelect(this.options.selectid,this.options.selecttext);
      
      
	  if(this.options.placeholder)
		  this.initSelect('',this.options.placeholder);

	  
      if(this.options.isshow)
      {
    	// console.log("begin show ....");
    	 _this.show();
      }
      
      //console.log("afterInit="+this.options.afterInit);
      if(this.options.afterInit)
    	{
    	//  console.log("this.options.afterInit");
    	  this.options.afterInit.apply(this);
    	}
      
    },

    filter: function(){
      var text = this.input.val();
      this.items.find('.searchable-select-item').addClass('searchable-select-hide');
      var datalist = this.items.find('.searchable-select-item:searchableSelectContains('+text+')');
      if(datalist && datalist.length >0)
    	  {
    	  	datalist.removeClass('searchable-select-hide');
    	  	//this.selectItem(datalist.first());
    	  	
    	  }
      
    //  console.log("this.currentSelectedItem:"+this.currentSelectedItem);
    
    	  	if(this.currentSelectedItem && this.currentSelectedItem.hasClass('searchable-select-hide') && this.items.find('.searchable-select-item:not(.searchable-select-hide)').length > 0){
    		    
    	  		this.hoverFirstNotHideItem();
    		  
    	  	}
    
      this.setPriviousAndNextVisibility();
    },

    hoverFirstNotHideItem: function(){
    	var ofirst = this.items.find('.searchable-select-item:not(.searchable-select-hide)').first();
    	//console.log(ofirst);
      this.hoverItem(this.items.find('.searchable-select-item:not(.searchable-select-hide)').first());
    },

    selectCurrentHoverItem: function(){
    	
      if(this.currentHoverItem && !this.currentHoverItem.hasClass('searchable-select-hide'))
        this.selectItem(this.currentHoverItem);
    },

    hoverPreviousItem: function(){
      if(!this.hasCurrentHoverItem())
        this.hoverFirstNotHideItem();
      else{
        var prevItem = this.currentHoverItem.prevAll('.searchable-select-item:not(.searchable-select-hide):first')
        if(prevItem.length > 0)
          this.hoverItem(prevItem);
      }
    },

    hoverNextItem: function(){
      if(!this.hasCurrentHoverItem())
        this.hoverFirstNotHideItem();
      else{
        var nextItem = this.currentHoverItem.nextAll('.searchable-select-item:not(.searchable-select-hide):first')
        if(nextItem.length > 0)
          this.hoverItem(nextItem);
      }
    },
   
  buildItems: function(){
      var _this = this;
      if(this.options.data)
    	{
    	  //console.log("has data ...");
    	 for(var ind=0; ind < this.options.data.length ; ind++)
    	 {
    		 var obj = this.options.data[ind];
    		 var valtemp = null;
    		 if(obj.id)
    			 valtemp=  obj.id;
    		 else
    			 valtemp = obj.label;
    		 var texttemp = obj.label;
    		// console.log(valtemp +":" + texttemp);
 	        var item = $('<div class="searchable-select-item" data-value="'+valtemp+'">'+texttemp+'</div>');
 	      

 	        item.on('mouseenter', function(){
 	          $(this).addClass('hover');
 	        }).on('mouseleave', function(){
 	          $(this).removeClass('hover');
 	        }).click(function(event){
 	          event.stopPropagation();
 	          _this.selectItem($(this));
 	          _this.hide();
 	        });

 	        _this.items.append(item);
 	      
    		 
    	 }
    	 
    	 
    	this.items.on('scroll', function(){
    	   _this.setPriviousAndNextVisibility();
    	  })

    	  	return ;
    	}
      
      this.element.find('option').each(function(){
        var item = $('<div class="searchable-select-item" data-value="'+$(this).attr('value')+'">'+$(this).text()+'</div>');

        if(this.selected){
          _this.selectItem(item);
          _this.hoverItem(item);
        }

        item.on('mouseenter', function(){
          $(this).addClass('hover');
        }).on('mouseleave', function(){
          $(this).removeClass('hover');
        }).click(function(event){
          event.stopPropagation();
          _this.selectItem($(this));
          _this.hide();
        });

        _this.items.append(item);
      });

      this.items.on('scroll', function(){
        _this.setPriviousAndNextVisibility();
      })
    },
    
	
    /*
    buildItems: function(){
      var _this = this;
      this.element.find('option').each(function(){
        var item = $('<div class="searchable-select-item" data-value="'+$(this).attr('value')+'">'+$(this).text()+'</div>');

        if(this.selected){
          _this.selectItem(item);
          _this.hoverItem(item);
        }

        item.on('mouseenter', function(){
          $(this).addClass('hover');
        }).on('mouseleave', function(){
          $(this).removeClass('hover');
        }).click(function(event){
          event.stopPropagation();
          _this.selectItem($(this));
          _this.hide();
        });

        _this.items.append(item);
      });

      this.items.on('scroll', function(){
        _this.setPriviousAndNextVisibility();
      })
    },
	*/
    show: function(){
      this.dropdown.removeClass('searchable-select-hide');
      this.input.focus();
      this.status = 'show';
      this.setPriviousAndNextVisibility();
      //console.log("invoke showed ...");
    },

    hide: function(){
    	//console.log("isshow:" +this.options.isshow );
    	if(this.options.isshow) return ;
    	
     if(!(this.status === 'show'))
        return;

      if(this.items.find(':not(.searchable-select-hide)').length === 0)
          this.input.val('');
      this.dropdown.addClass('searchable-select-hide');
      this.searchableElement.trigger('focus');
      this.status = 'hide';
    },
    initSelect:function(vid ,vtext)
    {
    	  //this.element.html("<option value='"+vid+"' selected='selected'>"+vtext+"</option>");
    	 var item = $('<div class="searchable-select-item" data-value="'+vid+'">'+vtext+'</div>');
    	 this.selectItem(item);
    },
    hasCurrentSelectedItem: function(){
      return this.currentSelectedItem && this.currentSelectedItem.length > 0;
    },

    selectItem: function(item){
      if(this.hasCurrentSelectedItem())
        this.currentSelectedItem.removeClass('selected');

      this.currentSelectedItem = item;
      item.addClass('selected');

      this.hoverItem(item);

      this.holder.text(item.text());
      var value = item.data('value');
      this.holder.data('value', value);
     // this.element.value=value;
    //  console.log("searchableElement:"+this.searchableElement);
     
      this.element.html("<option value='"+value+"' selected='selected'>"+item.text()+"</option>");
     // var checkText=this.element.find("option:selected").text(); 
     // console.log("this.element:" + this.element.val() +" text:" + checkText);
      if(this.options.afterSelectItem){
        this.options.afterSelectItem(value,item.text());
      }
      
      if(value !=null && value !='')
       {
    	  this.searchableElement.find(".searchable-select-close").css("display","block");
    	  
       }else
    	   this.searchableElement.find(".searchable-select-close").css("display","none");
 	 
    },

    hasCurrentHoverItem: function(){
      return this.currentHoverItem && this.currentHoverItem.length > 0;
    },

    hoverItem: function(item){
      if(this.hasCurrentHoverItem())
        this.currentHoverItem.removeClass('hover');

      if(item.outerHeight() + item.position().top > this.items.height())
        this.items.scrollTop(this.items.scrollTop() + item.outerHeight() + item.position().top - this.items.height());
      else if(item.position().top < 0)
        this.items.scrollTop(this.items.scrollTop() + item.position().top);

      this.currentHoverItem = item;
      item.addClass('hover');
    },

    setPriviousAndNextVisibility: function(){
      if(this.items.scrollTop() === 0){
        this.hasPrivious.addClass('searchable-select-hide');
        this.scrollPart.removeClass('has-privious');
      } else {
        this.hasPrivious.removeClass('searchable-select-hide');
        this.scrollPart.addClass('has-privious');
      }

      if(this.items.scrollTop() + this.items.innerHeight() >= this.items[0].scrollHeight){
        this.hasNext.addClass('searchable-select-hide');
        this.scrollPart.removeClass('has-next');
      } else {
        this.hasNext.removeClass('searchable-select-hide');
        this.scrollPart.addClass('has-next');
      }
    }
  });

  $.fn.searchableSelect = function(options){
    this.each(function(){
      var sS = new $sS($(this), options);
    });

    return this;
  };

})(jQuery);
