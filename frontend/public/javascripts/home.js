//Make Canvases
(function() {

    if(!('getContext' in document.createElement('canvas'))){
        alert('Sorry, it looks like your browser does not support canvas!');
        return false;
    }

    var maxCanvases = 15;
    var currCanvases = 0;
    var canvW = 80;
    var canvH = 160;
    var pixelsInCanv = canvW * canvH;

    var partitionSz = 10;
    var rowLen = 8;
    var fvHeight = 16;
    var blockSz = partitionSz;

    var allCanvases = new Array();

    // Creates a new canvas element and appends it as a child
    // to the parent element, and returns the reference to
    // the newly created canvas element
    function createCanvas(parent, width, height) {
        var canvas = {};
        canvas.node = document.createElement('canvas');
        canvas.context = canvas.node.getContext('2d');
        canvas.node.width = width || 80;
        canvas.node.height = height || 160;
        canvas.node.style.margin = '1em 0.5em 0 0.5em';
        canvas.node.style.border = "1px solid black";
        parent.appendChild(canvas.node);
        return canvas;
    }

    function init(container, width, height, fillColor) {
        var canvas = createCanvas(container, width, height);
        allCanvases.push(canvas);
        var ctx = canvas.context;
        
        ctx.fillCircle = function(x, y, radius, fillColor) {
            this.fillStyle = fillColor;
            this.beginPath();
            this.moveTo(x, y);
            this.arc(x, y, radius, 0, Math.PI * 2, false);
            this.fill();
        };
        ctx.clearTo = function(fillColor) {
            ctx.fillStyle = fillColor;
            ctx.fillRect(0, 0, width, height);
        };
        ctx.clearTo(fillColor || "#ddd");

        // bind mouse events
        canvas.node.onmousemove = function(e) {
            if (!canvas.isDrawing) {
               return;
            }
            var x = e.pageX - this.offsetLeft;
            var y = e.pageY - this.offsetTop;
            var radius = 8;
            var fillColor = '#ff0000';
            ctx.fillCircle(x, y, radius, fillColor);
            //set that canvas has been touched
            canvas.hasDrawn = true;
        };
        canvas.node.onmousedown = function(e) {
            canvas.isDrawing = true;
        };
        canvas.node.onmouseup = function(e) {
            canvas.isDrawing = false;
        };
    }

    //creates a button that when clicked adds more canvases
    var container = document.getElementById('myList');
    var button = document.getElementById('addLetterButton');
    button.onclick = function() {
        if (currCanvases < maxCanvases) {
            init(container, canvW, canvH, '#ddd');
            ++currCanvases;
        }
        return false;
    };

    //sends serialized canvas data to server
    //shade of grey used is 221
    //use every 4th value
    var senderButton = document.getElementById('sendToServer');
    senderButton.onclick = function() {
        var toServerObj = {};
        var upperBound = (pixelsInCanv-1)*4;
        var i;
        for (i = 0; i != allCanvases.length; ++i) {
            if (allCanvases[i].hasDrawn) {
                var canv = allCanvases[i].node;
                var data = allCanvases[i].context.getImageData(0, 0, canv.width, canv.height);
                toServerObj[i.toString()] = reduceCanvasToFV(data.data).toString();
            } else {
                break;
            }
        }
        if (!jQuery.isEmptyObject(toServerObj)) {
            alert(JSON.stringify(toServerObj));
            $.ajax({
                url: '/data',
                type: 'GET',
                data: toServerObj,
                success: function(string) {alert(string);}
            });
        } else {
            alert("Submitted word format invalid. " +
                "Perhaps a blank letter is in a position where it should not be in. " +
                "The first letter should not be blank.");
        }
        return false;
    }

    //compresses canvas to 32 length feature vector
    function reduceCanvasToFV(imageDataArr) {
        var interArr = new Array();
        var j = 0;
        var csum = 0;
        //i is column
        //j compresses 20 cells at once
        for (var i = 0; i != imageDataArr.length; i+=4) {
            if (j == blockSz) {
                interArr.push(csum);
                j = 0;
                csum = 0;
            }
            csum += imageDataArr[i];
            //csum += imageDataArr[i+1];
            //csum += imageDataArr[i+2];
            //csum += imageDataArr[i+3];
            ++j;
        }
        //edge case for last pixel
        interArr.push(csum);

        var featureVector = new Array();
        var distBw = interArr.length / fvHeight;
        current = 0;
        for (var i = 0; i != fvHeight; ++i) {
            for (j = 0; j != rowLen; ++j) {
                cSum = 0;
                initial = i*distBw+j;
                for (var k = initial; k != initial+(rowLen*blockSz); k+=rowLen) {
                    cSum += interArr[k];
                }
                featureVector.push(cSum);
            }
        }

        //convert to 0's and 1's depending on below/above threshold
        for (var i = 0; i != featureVector.length; ++i) {
            //221 is grey color; 400 blockSz*blockSz is each compressed block
            //can be adjusted
            if (featureVector[i] > blockSz*blockSz*235)
                featureVector[i] = 1;
            else 
                featureVector[i] = 0;
        }

        //128 length vector
        return featureVector;
    }

    //clear inputs
    document.getElementById('clear').onclick = function() {
        clear = function(ctx, fillColor) {
            ctx.fillStyle = fillColor;
            ctx.fillRect(0, 0, width, height);
        };
        for (var i = 0; i != allCanvases.length; ++i) {
            var canv = allCanvases[i].node;
            var ctx = allCanvases[i].context;
            ctx.fillStyle = "#ddd";
            ctx.fillRect(0, 0, canv.width, canv.height);
        }
    }

}());