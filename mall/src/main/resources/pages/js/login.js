      /** 
       * 产生成验证码 页面加载时产生
       * */
      function randomWord(n){
        var str = "",
            arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
        for(var i=0; i<n; i++){
            pos = Math.round(Math.random() * (arr.length-1));
            str += arr[pos];
        }
        return str;
    }
         var str = randomWord(6);
        document.getElementById('str').innerHTML =str;
         
        function randomColor(){
          arr = ['red','orange','yellow','green','blue','indigo','purple'];
         var s = Math.round(Math.random() * (arr.length-1));
         return arr[s];
        }
        // alert(randomColor);
        document.getElementById('str').style.color=randomColor();
      
    /**
     * 点击验证码重新生成一个新的验证码
     */
       function rand(){
    
         str = randomWord(6);
        document.getElementById('str').innerHTML =str;
        // alert(randomColor);
        document.getElementById('str').style.color=randomColor();
        return str;
        }
    
        /**
         * 输入验证码后验证 输入的验证码是否正确
         */
        function randEx(){
          var vs = document.getElementById('user_code').value;
            if(str.toUpperCase()==vs.toUpperCase()){
            //   alert(true);  //正确
            // document.getElementById('code_true').style.visibility='visible';
                // 抽取一个显示的函数
                visibleTrue('code_true');
            }else{     
                // alert(false); //错误   先将隐藏的元素 code_true > display设置为none在显示
                // document.getElementById('code_true').style.display='none';
                // document.getElementById('code_false').style.visibility='visible';
                // document.getElementById('code_false').style.color='red';
                //抽取一个函数 visibleFalse
                visibleFalse('code_true','code_false');
            }
        }

        /**
         * 设置显示的元素ID
         * @param {*} str  
         */ 
        function visibleTrue( str ){
            document.getElementById(str).style.visibility='visible';
        }
        /**
         * 设置隐藏和显示的元素  
         * @param {*} hied 隐藏的元素 ID
         * @param {*} visible  显示的元素 ID
         */
       function visibleFalse(hied,visible){
        document.getElementById(hied).style.display='none';
        document.getElementById(visible).style.visibility='visible';
        document.getElementById(visible).style.color='red';
       }
       /**
        * 验证输入的账号
        */
        function userNameEx(){
            var text = document.getElementById('user_name').value;
            // alert(text);
            if(text==null || text==""){   //这里验证 电话号码和email 的合理性
                visibleFalse('name_true','name_false');
            }else{
                visibleTrue('name_true');
            }
        }
      
        /**
         * 验证输入的密码是否为空
         */
         function userPasswordEx(){
            var text = document.getElementById('user_passwords').value;
            // alert(text);
            if(text==null || text==""){   //这里验证 电话号码和email 的合理性
                visibleFalse('password_true','password_false');
            }else{
                visibleTrue('password_true');
            }
        }

        