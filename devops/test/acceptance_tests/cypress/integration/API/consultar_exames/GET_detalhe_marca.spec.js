/// <reference types="Cypress"/>

describe('Consultar detalhes das marcas via API', () => {
    let token;

    beforeEach(() => {
        cy.gerar_token().then((resp) => {
            console.log(resp)
            expect(resp.status).to.equal(200)
            expect(resp.statusText).to.equal('OK')
            token = resp.body.access_token && `Bearer ${resp.body.access_token}`
        })
    })

    // Este teste corresponde ao CT PDC-79 no JIRA
    it.skip('GET_ID - Deve recuperar os detalhes da marca', () => {
        //Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e Id da marca.
        cy.visualizar_detalhes_da_marca(token, 1).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.eq(200)
            expect(resp.body).deep.eq({
                "id": 1,
                "mnemonico": "Delboni",
                "nome": "Delboni Auriemo",
                "segmento": "Executive",
                "url": "http://www.delboniauriemo.com.br/",
                "whatsapp": "11 3049-6999",
                "urlAgendamento": "agendamentoonline.delboniauriemo.com.br/",
                "urlLaudo": "delboniauriemo.com.br/resultados-de-exames",
                "ufs": [
                    "SP"
                ]
            })
        })
    })

    // Este teste corresponde ao CT PDC-80 no JIRA
    it('GET_ID - Deve retornar erro quando informar id_marca inválido', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e id_marca.
        cy.visualizar_detalhes_da_marca(token, 2061, 9999).then(resp => {
            console.log(resp)
            expect(resp.status).to.eq(400)
            expect(resp.body.statusCode).to.eq(204)
            expect(resp.body.errors[0].message).to.eq('Unidades não encontrado!')
        })
    })

    // Este teste corresponde ao CT PDC-81 no JIRA
    it('GET_ID - Deve retornar erro quando não informar um id_marca válido', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e id_marca.
        cy.visualizar_detalhes_da_marca(token, 2061).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.eq(500)
            expect(resp.body.statusCode).to.eq(500)
            expect(resp.body.errors[0].message).to.eq('Desculpe, ocorre um erro. Analise o detalhe logo abaixo.')
        })
    })

    // Este teste corresponde ao CT PDC-82 no JIRA
    it.skip('GET_ID - Deve recuperar as marcas onde o exame esta ativo', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e id_exame.
        cy.visualizar_detalhes_do_exame(token, 7332).then(resp => {
            console.log(resp.body.marcasAtivas)
            expect(resp.status).to.equal(200)
            expect(resp.body.marcasAtivas).exist
        })
    })

    // Este teste corresponde ao CT PDC-83 no JIRA
    it.skip('GET_ID - Deve recuperar as marcas onde o exame não esta ativo', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e id_exame.
        cy.visualizar_detalhes_do_exame(token, 7332).then(resp => {
            console.log(resp.body.marcasInativas)
            expect(resp.status).to.equal(200)
            expect(resp.body.marcasInativas).exist
        })
    })

    // Este teste corresponde ao CT PDC-60 no JIRA
    it('GET_ID - Deve recuperar as unidades por marcas vinculadas ao exame', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token, id_exame e id_marca.
        cy.visualizar_detalhes_da_marca(token, 7332, 1).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.eq(200)
            expect(resp.body.unidades).exist

        })
    })

    it('GET_ID - Deve recuperar o preço e o prazo do exame', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token, id_exame e id_marca.
        cy.visualizar_detalhes_da_marca(token, 7332, 1).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.equal(200)
            expect(resp.body.unidades[1].preco).exist
            expect(resp.body.unidades[1].prazoHora).exist
            expect(resp.body.unidades[1].prazoHoraUrgente).exist


        })
    })

})