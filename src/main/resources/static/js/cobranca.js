$("#confirmacaoExclusaoModal").on("show.bs.modal", function (event) {
  var button = $(event.relatedTarget);

  var codigoTitulo = button.data("codigo");
  var descricaoTitulo = button.data("descricao");

  var modal = $(this);
  var form = modal.find("form");
  var action = form.attr("action");

  //corrigindo um bug ao clicar em excluir e desistir.
  //o js estava adicionando o id mais de uma vez

  if (!action.endsWith("/titulos")) {
    action = "/titulos";
  }

  form.attr("action", action + "/" + codigoTitulo);

  modal
    .find(".modal-body span")
    .html(
      "Tem certeza que deseja apagar o titulo <strong> " +
        descricaoTitulo +
        " </strong>"
    );
});
