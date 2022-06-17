INSERT INTO permissao (id, nome) VALUES (1, 'CADASTRAR_CIDADE');
INSERT INTO permissao (id, nome) VALUES (2, 'CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (permissao_id, grupo_id) VALUES (1, 1);
INSERT INTO grupo_permissao (permissao_id, grupo_id) VALUES (2, 1);

INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES (
	(SELECT id FROM usuario WHERE email = 'leandro.merlo@gmail.com'), 1
);

