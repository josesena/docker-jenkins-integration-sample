/// <reference types="Cypress"/>

Cypress.Commands.add('gerar_token', () => {
    cy.request({
      method: 'POST',
      url: `${Cypress.env('url_token')}portal-cadastro/protocol/openid-connect/token`,
      headers: {
          'Content-Type':'application/x-www-form-urlencoded',
      },
      body: Cypress.env('dados_do_usuario'),
      failOnStatusCode: false
    })
})

Cypress.Commands.add('visualizar_detalhes_do_exame', (token, id_do_exame) => {
    cy.request({
        method: 'GET',
        url: `${id_do_exame}`,
        headers: {
            Authorization: token,
            'Content-Type':'application/x-www-form-urlencoded',
        },
        failOnStatusCode: false
    })
})

Cypress.Commands.add('visualizar_detalhes_da_marca', (token, id_do_exame, id_da_marca) => {
    cy.request({
        method: 'GET',
        url: `${id_do_exame}/marca/${id_da_marca}/unidades`,
        headers: {
            Authorization: token,
            'Content-Type':'application/x-www-form-urlencoded',
        },
        failOnStatusCode: false
    })
})