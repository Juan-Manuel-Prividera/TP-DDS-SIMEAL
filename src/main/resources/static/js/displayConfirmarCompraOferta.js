document.getElementById("confirmarCompraBtn").addEventListener("click", function() {
    const miDiv = document.getElementById("confirmacionDisplay");
    miDiv.style.display = "block";
    const comprarBtn = document.getElementById("confirmarCompraBtn");
    comprarBtn.style.display = "none";
})

document.getElementById("cancelarBtn").addEventListener("click", function() {
    const miDiv = document.getElementById("confirmacionDisplay");
    miDiv.style.display = "none";
    const comprarBtn = document.getElementById("confirmarCompraBtn");
    comprarBtn.style.display = "block";
})

/*
document.getElementById("confirmarBtn").addEventListener("click", function() {
    const miDiv = document.getElementById("confirmacionDisplay");
    miDiv.style.display = "none";
})
 */
