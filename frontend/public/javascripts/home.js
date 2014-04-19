
/*var xStart,
xEnd,
yStart,
yEnd,
paint,
ctx;
$(document).ready(function () {

    if (typeof FlashCanvas != "undefined") {
        FlashCanvas.initElement($('canvas')[0]);
    }
    ctx = $('canvas')[0].getContext("2d");
    ctx.strokeStyle = '#000';
    ctx.lineJoin = "round";
    ctx.lineCap = "round";
    ctx.lineWidth = 1;


    $('canvas').bind('mousedown mousemove mouseup mouseleave touchstart touchmove touchend', function (e) {
        var orig = e.originalEvent;

        if (e.type == 'mousedown') {
            e.preventDefault();
            e.stopPropagation();

            xStart = e.clientX - $(this).offset().left;
            yStart = e.clientY - $(this).offset().top;
            xEnd = xStart;
            yEnd = yStart;

            paint = true;
            draw(e.type);

        } else if (e.type == 'mousemove') {
            if (paint == true) {
                xEnd = e.clientX - $(this).offset().left;
                yEnd = e.clientY - $(this).offset().top;


                // lineThickness = 5 - Math.sqrt((xStart - xEnd) *(xStart-xEnd) + (yStart - yEnd) * (yStart-yEnd))/10;
                // if(lineThickness < 1){
                //     lineThickness = 1;   
                // }

                lineThickness = 1 + Math.sqrt((xStart - xEnd) * (xStart - xEnd) + (yStart - yEnd) * (yStart - yEnd)) / 5;



                if (lineThickness > 10) {
                    lineThickness = 10;
                }

                ctx.lineWidth = lineThickness;
                draw(e.type);
            }
        } else if (e.type == 'mouseup') {
            paint = false;
        } else if (e.type == 'mouseleave') {
            paint = false;
        } else if (e.type == 'touchstart') {
            if (orig.touches.length == 1) {
                e.preventDefault();
                e.stopPropagation();

                xStart = orig.changedTouches[0].pageX - $(this).offset().left;
                yStart = orig.changedTouches[0].pageY - $(this).offset().top;
                xEnd = xStart;
                yEnd = yStart;

                paint = true;
                draw(e.type);
            }
        } else if (e.type == 'touchmove') {
            if (orig.touches.length == 1) {
                if (paint == true) {
                    xEnd = orig.changedTouches[0].pageX - $(this).offset().left;
                    yEnd = orig.changedTouches[0].pageY - $(this).offset().top;



                    var x1 = xEnd,
                        x2 = xStart,
                        y1 = yEnd,
                        y2 = yStart;


                    lineThickness = 1 + Math.sqrt((xStart - xEnd) * (xStart - xEnd) + (yStart - yEnd) * (yStart - yEnd)) / 6;
                    if (lineThickness > 10) {
                        lineThickness = 10;
                    }


                    ctx.lineWidth = lineThickness;


                    draw(e.type);
                }
            }
        } else if (e.type == 'touchend') {
            paint = false;
        }

    });

    
});

function draw(event) {

    if (event == 'mousedown') {
        ctx.beginPath();
        ctx.moveTo(xStart, yStart);
        ctx.lineTo(xEnd, yEnd);
        ctx.stroke();
    } else if (event == 'mousemove') {
        ctx.beginPath();
        ctx.moveTo(xStart, yStart);
        ctx.lineTo(xEnd, yEnd);
        ctx.stroke();
    } else if (event == 'touchstart') {
        ctx.beginPath();
        ctx.moveTo(xStart, yStart);
        ctx.lineTo(xEnd, yEnd);
        ctx.stroke();
    } else if (event == 'touchmove') {
        ctx.beginPath();
        ctx.moveTo(xStart, yStart);
        ctx.lineTo(xEnd, yEnd);
        ctx.stroke();
    }
    xStart = xEnd;
    yStart = yEnd;
}

function canvasBind(e) {} */
var act = null;
var contexts = [];
var draw = false;
var c = false;

function createLayer (w, h) {

    var canvas = document.createElement('canvas');

    canvas.width = w;
    canvas.height = h;
    canvas.style.width = w + "px";
    canvas.style.height = h + "px";
    canvas.style.position = "absolute";

    var body = document.body.appendChild('<div>' + canvas + '</div>');

    return canvas;
}

function boot() {
/*
$.each( obj, function( key, value ) {
  alert( key + ": " + value );
});*/
    
    $('.cvs')
        .each(function() {
            id = this.id;
            contexts[id] = this.getContext('2d');
        })
        .mouseout(function() {
            c=false;
        })
        .mousedown(function(){
            draw = true;
        })
        .mouseup(function(){
            draw = false;
        })
        .mousemove(function(ev){
            var id = this.id,
                o = $(this).offset(),
                x = (ev.pageX - o.left),
                y = (ev.pageY - o.top);

            if(draw && contexts[id] != null) {
                if (!c) {
                    contexts[id].beginPath();
                    contexts[id].moveTo(x, y);
                    c = true;
                } else {
                    contexts[id].lineTo(x, y);
                    contexts[id].stroke();
                }
            }
        });
}

$(document).ready(boot);