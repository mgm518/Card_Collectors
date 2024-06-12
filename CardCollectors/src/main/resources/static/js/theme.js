const navicon = document.querySelector(".navicon");
const theme = document.getElementById("toggleTheme");

theme.addEventListener("change", () => {
  localStorage.setItem("theme", theme.checked);
  document.body.classList.toggle("dark");
  navicon.classList.toggle("fa-moon");
  navicon.classList.toggle("fa-sun");
});

if (localStorage.getItem("theme") === "true") {
  theme.checked = true;
  document.body.classList.toggle("dark");
  navicon.classList.toggle("fa-moon");
  navicon.classList.toggle("fa-sun");
}
