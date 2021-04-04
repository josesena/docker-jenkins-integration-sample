/// <reference types="Cypress"/>

describe('Consultar exames via API', () => {
    let token;

    beforeEach(() => {
        cy.gerar_token().then((resp) => {
            console.log(resp)
            expect(resp.status).to.equal(200)
            expect(resp.statusText).to.equal('OK')
            token = resp.body.access_token && `Bearer ${resp.body.access_token}`
        })
    })

    // Este teste corresponde ao CT PDC-75 no JIRA
    it('GET_ID - Deve recuperar os detalhes do exame', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e Id do exame.
        cy.visualizar_detalhes_do_exame(token, 4758).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.equal(200)
            expect(resp.body).to.have.property('nome', 'CITOMEGALOVÍRUS , PESQUISA, DIVERSOS (XXX), PCR EM TEMPO REAL')
            expect(resp.body).to.have.property('sinonimias')
            expect(resp.body).to.have.property('categoria')
            expect(resp.body).to.have.property('material')
            expect(resp.body).to.have.property('marcasAtivas')
            expect(resp.body).to.have.property('marcasInativas')
            expect(resp.body).to.have.property('codTuss')
            expect(resp.body).to.have.property('descricaoTuss')
            expect(resp.body).to.have.property('grupoTuss')
            expect(resp.body).to.have.property('especialidade')
            expect(resp.body).to.have.property('subEspecialidade')
            expect(resp.body).to.have.property('preparos')
            expect(resp.body).to.have.property('medinc')
            expect(resp.body).to.have.property('loinc')
        })
    })

    // Este teste corresponde ao CT PDC-39 no JIRA
    it('GET_ID - Deve retornar erro quando informar id_exame inválido', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e Id do exame.
        cy.visualizar_detalhes_do_exame(token, 0).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.equal(400)
            expect(resp.body.statusCode).to.eq(204)
            expect(resp.body.errors[0].message).to.eq('Detalhe do exame não encontrado.')
        })
    })

    // Este teste corresponde ao CT PDC-78 no JIRA
    it('GET_ID - Deve retornar erro quando não informar um id_exame válido na pesquisa', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetro informado: Token.
        cy.visualizar_detalhes_do_exame(token).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.equal(500)
            expect(resp.body.statusCode).to.eq(500)
            expect(resp.body.errors[0].message).to.eq('Desculpe, ocorre um erro. Analise o detalhe logo abaixo.')
        })
    })

    // Este teste corresponde ao CT PDC-93 no JIRA
    it.skip('GET_ID - Deve recuperar os detalhes do exame informando um Id_Dasa válido', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e Id_dasa.
        cy.visualizar_detalhes_do_exame(token, 'id_dasa').then(resp => {
            console.log(resp.body.id_dasa)
            expect(resp.status).to.eq(200)
            expect(resp.body.id_dasa).to.eq('id_dasa')
        })
    })

    // Este teste corresponde ao CT PDC-94 no JIRA
    it.skip('GET_ID - Deve recuperar os detalhes do exame informando um mnemônico válido', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e mnemônico.
        cy.visualizar_detalhes_do_exame(token, 'mnemônico').then(resp => {
            console.log(resp.body.mnemônico)
            expect(resp.status).to.eq(200)
            expect(resp.body.mnemônico).to.eq('mnemônico')
        })
    })

    // Este teste corresponde ao CT PDC-95 no JIRA
    it.skip('GET_ID - Deve recuperar os detalhes do exame informando o código TUSS', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e código TUSS.
        cy.visualizar_detalhes_do_exame(token, 'cod_MEDINC').then(resp => {
            console.log(resp.body.cod_MEDINC)
            expect(resp.status).to.eq(200)
            expect(resp.body.cod_TUSS).to.eq('cod_TUSS')
        })
    })

    // Este teste corresponde ao CT PDC-96 no JIRA
    it.skip('GET_ID - Deve recuperar os detalhes do exame informando o código MEDINC', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e código MEDINC.
        cy.visualizar_detalhes_do_exame(token, 'cod_MEDINC').then(resp => {
            console.log(resp.body.cod_MEDINC)
            expect(resp.status).to.eq(200)
            expect(resp.body.cod_MEDINC).to.eq('cod_MEDINC')
        })
    })

    // Este teste corresponde ao CT PDC-97 no JIRA
    it.skip('GET_ID - Deve recuperar os detalhes do exame informando o código SAP', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e código SAP.
        cy.visualizar_detalhes_do_exame(token, 'cod_SAP').then(resp => {
            console.log(resp.body.cod_SAP)
            expect(resp.status).to.eq(200)
            expect(resp.body.cod_SAP).to.eq('cod_SAP')
        })
    })

    // Este teste corresponde ao CT PDC-98 no JIRA
    it.skip('GET_ID - Deve retornar erro quando informar id_Dasa inválido', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetros informados: Token e id_Dasa.
        cy.visualizar_detalhes_do_exame(token, 'id_Dasa_invalido').then(resp => {
            console.log(resp.body.cod_SAP)
            expect(resp.status).to.eq(400)
            expect(resp.body).deep.eq({
                "statusCode": 404,
                "errors": [
                    {
                        "code": "detalhe-nao-encontrado",
                        "message": "Nenhuma mensagem disponivel"
                    }
                ]
            })
        })
    })

    // Este teste corresponde ao CT PDC-99 no JIRA
    it.skip('GET_ID - Deve retornar erro quando não informar um Id_Dasa válido na pesquisa', () => {
        // Comando utilizado para visualizar os detalhes do exame,
        // Parâmetro informado: Token.
        cy.visualizar_detalhes_do_exame(token).then(resp => {
            console.log(resp.body)
            expect(resp.status).to.equal(500)
            expect(resp.body).deep.eq({
                "statusCode": 500,
                "errors": [
                    {
                        "code": "erro-generico",
                        "message": "Nenhuma mensagem disponivel"
                    }
                ]
            })
        })
    })
})
