document.getElementById("confirmarCompraBtn").addEventListener("click", function() {
    const miDiv = document.getElementById("confirmacionDisplay");
    miDiv.style.display = "block";
})

document.getElementById("cancelarBtn").addEventListener("click", function() {
    const miDiv = document.getElementById("confirmacionDisplay");
    miDiv.style.display = "none";
})

/*
document.getElementById("confirmarBtn").addEventListener("click", function() {
    const miDiv = document.getElementById("confirmacionDisplay");
    miDiv.style.display = "none";
})
 */
