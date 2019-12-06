var leaderboardHead=document.getElementById("leaderboardHead");
var params = new URLSearchParams(location.search)
var gamePlayerId = params.get("gp")
var gpIds=[]
var nn

$(function() {

  // display text in the output area
  function showOutput(text) {
    $("#output").text(text);
  }
var lista= document.getElementById("lista");
var leaderboardBody= document.getElementById("leaderboardBody");
var games
//function loadGames(){
$.get("/api/games")
.done(function (gamesx) {

    //pause(10)
    console.log(gamesx)
    nn=gamesx.player.id
    console.log(nn)
    makeList(gamesx);
    makeLeaderboard(gamesx.games);
    loadPlayers(gamesx.games)
})
.fail(function( jqXHR, textStatus ) {
 showOutput( "Failed: " + textStatus );
 return;
});

//}

});
//console.log()




function loadPlayers(games){
$.get("api/players")
.done(function(playersx){
    console.log(playersx)
    pause(12)
    takeNames(playersx.players,games)
})
.fail(function( jqXHR, textStatus){
showOutput("Failed:"+textStatus);
});
}

function makeList (data){
    let aux = ""
    for (i = 0; i < data.games.length; i++) {
       let aux2 = ""
        for (j = 0; j < data.games[i].gamePlayers.length; j++) {
            //gpIds.push(data[i].gamePlayers[j].id)

            if (data.player!=null){
                makeAux3xD(nn,data)
            }

            if(nn==data.games[i].gamePlayers[j].player.id){
               var href= data.games[i].gamePlayers[j].id
                if (j==1){
                    aux2 += " vs " + data.games[i].gamePlayers[j].player.name
                    }
                else{
                    aux2+=data.games[i].gamePlayers[j].player.name
                    }

             //makeLinks (aux2, data)
            aux +=`<li><a href= ${aux3}> game ${data.games[i].id} was created on ${data.games[i].date}: ${aux2}</a></li>`
            }
            else {
                if (j==1){
                 aux2 += " vs " + data.games[i].gamePlayers[j].player.name
                }
                else{
                   aux2+=data.games[i].gamePlayers[j].player.name
                }
            aux+=`<li>game ${data.games[i].id} was created on ${data.games[i].date}: ${aux2}</li>`
            }

            if (data.games[i].gamePlayers.length <=1) {
             aux+= `<button id= 'hipersusvin'+${{nn}}> join game </button>`
            }
          /*else {
            aux +=`<li>game ${data.games[i].id} was created on ${data.games[i].date}: ${aux2}</a></li>`
            }*/

        }

    }

    lista.innerHTML = aux

};

function makeLeaderboard(data){

leaderboardHead.innerHTML+=`<tr><th>Player</th><th>Total Score</th><th>Wins</th><th>Ties</th><th>Loses</th></tr>`
}

let aux3
function makeAux3xD (nn,data){
    if (data.player!=null){
        if (nn==data.games[i].gamePlayers[j].player.id){
            aux3= "http://localhost:8080/games" + data.games[i].gamePlayers[j].id+"/gamePlayers"
        }
    }
}

function getScores(id,games) {
var score=[0,0,0,0]
for (var z=0;z<games.length;z++){
    for(var j=0;j<games[z].gamePlayers.length;j++){
    games[z].gamePlayers[j].player.id
    if (id==games[z].gamePlayers[j].player.id){
    score[0]+=games[z].gamePlayers[j].score
        if (games[z].gamePlayers[j].score==1){
        score[1]++}
        else if (games[z].gamePlayers[j].score==0.5){
        score[2]++}
        else{
        score[3]++}
    }

   }
}
return score
}
//make links in list/*
/*function makeLinks (aux,data){
    if (nn==data[i].gamePlayers[j].player){
        //aux= '<a href="http://localhost:8080/api/game_view/"'+ nn + ">"
        aux+= "HOla que onda"
    }
}*/



function takeNames (players,games){
var aux=``
for (var i=0;i<players.length;i++) {
    var score=getScores(players[i].id,games);
    aux+=`<tr id=${players[i].name}><td>${players[i].name}</td><td>${score[0]}</td><td>${score[1]}</td><td>${score[2]}</td><td>${score[3]}</td></tr>`
  }
  leaderboardBody.innerHTML+=aux

}


function login(username,password) {
    var ok=false;
    $.post("/api/login", {
        username: username,
        password: password
    })
    .done(ok=function(){
       console.log("logged in");
       return true;

   })
    .fail(function(){
        console.log("failed")
    })
    if (ok) {
           document.getElementById("logOut").style.visibility= 'visible';
           document.getElementById("newUser").style.visibility= 'hidden';
           document.getElementById("signOut").style.visibility= 'visible';
           document.getElementById("templateLogIn").style.visibility = 'hidden';
           document.getElementById("welcome").innerHTML= "<p>" + "Welcome "+username + "<p>";
           }

    return false;
}

function logOut(){
   var ok=false;
    $.post("/api/logout")
    .done(ok=function(){
        console.log("logged out")
        return true;
    })
    .fail(function(){
        console.log("failed")
    })
    if (ok) {
           document.getElementById("logOut").style.visibility = 'hidden';
           document.getElementById("templateLogIn").style.visibility = 'visible';
           document.getElementById("welcome").innerHTML= "<p>" + "Welcome stranger. Please log in"+ "<p>";
           }
    return false
}

function showSignIn() {
document.getElementById("templateSignIn").style.visibility="visible";
}



function signIn (username, email, password){
   var ok=false;
    $.post("/api/players",{
       userName: username,
       email: email,
       password: password
       })
       .done(ok=function(){
              console.log("registered ;)");
              return true;}
              )
        //.done(res => console.log("registered ;)")
        .fail(error => console.log(error))
        if (ok) {
            login(username,password)
        }
        return false
}
function pause(duration) {
  var start = new Date().getTime();
  while (new Date().getTime() - start < duration);
}
/*function logIn (form) {
   // var form= new FormData(document.getElementById("logIn"))
   var formData=new FormData(form);

    fetch ("/api/login", {
              method:"POST",
               body:formData
    }).then(function(response) {
    if(response.ok) {
        alert("success bro");
        getGames();
        console.log("holis");
        document.getElementById(templateLogIn).hidden;
        document.getElementById(logOut).disabled=false;

    } else {
        console.log(response.statusText);
        throw new Error(response.statusText);
    }
    }).catch(function(error){
        alert("log in failed"+error.message);
        console.log("log in failed")
    })
    return false;
}
//logout
function logOut (){
    fetch("/api/",{
        method:"POST"
        })
        .then (res=>{
            if(res.ok){
                getGames();
                }
         })
        .catch(error=>console.log(error))
};*/
//signup

/*function signup(){
let form= new FormData(document.getElementById("signIn"));
let info= new FormData();

info.append ("userName", form.get("userName"));
info.append("email", form.get("email"))
info.append("password", form.get("password"))
fetch ("/api/players",{
method:"POST",
body:form
}).then (function(response){
    if (response.ok){
    alert("Log in Succesfull");
    }else {
    return Promise.reject(response.json())
    }
    }).catch(function (error){error.then(x=> console.log(x))
    });
}*/



//show or hide forms
//log in
//function showHideForms {
//    if (player!=guest){


//}
//}