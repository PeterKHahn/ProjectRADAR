$(document).ready(() => {
	let mapChanger = $("#map-changer");

	$("#submit-button").click(() => {
		let vl = $("input[name=map]:checked").val()
		const postParameters = {
			map : vl
		}
		console.log(vl)

		$.post('/change-map', postParameters, responseJSON => {

		})
	})

});
