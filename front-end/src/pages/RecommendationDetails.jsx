import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {api} from "../api/api.js";

function RecommendationDetails() {
    const { hobbyId } = useParams();

    const [detalhes, setDetalhes] = useState(null);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState("");
    const [gerandoPlano, setGerandoPlano] = useState(false);
    const [erroPlano, setErroPlano] = useState("");


    const carregarDetalhes = async () => {
        try {
            setCarregando(true);
            setErro("");

            const response = await api.get(
                `/recommendations/${hobbyId}/details`
            );

            setDetalhes(response.data);

            } catch (error) {
                console.error(error);
                setErro(
                    "Não foi possível carregar os detalhes do hobby."
                );
            } finally {
                setCarregando(false);
            }
    };


    useEffect(() => {
        carregarDetalhes();
    }, [hobbyId]);

    const gerarPlano = async () => {
            try {
                setGerandoPlano(true);
                setErroPlano("");

                await api.post(
                    `/ai/plan/${hobbyId}`
                );

                await carregarDetalhes();

            } catch (error) {
                console.error(error);

                setErroPlano(
                    error.response?.data?.error ??
                    "Não foi possível gerar o plano personalizado."
                );
            } finally {
                setGerandoPlano(false);
            }
        };

        const regenerarPlano = async () => {
            try {
                setGerandoPlano(true);
                setErroPlano("");

                await api.post(
                    `/ai/plan/${hobbyId}/regenerate`
                );

                await carregarDetalhes();

            } catch (error) {
                console.error(error);

                setErroPlano(
                    error.response?.data?.error ??
                    "Não foi possível atualizar o plano personalizado."
                );
            } finally {
                setGerandoPlano(false);
            }
        };

    if (carregando) {
        return <p>Carregando...</p>;
    }

    if (erro) {
        return <p>{erro}</p>;
    }

    if (!detalhes) {
        return <p>Hobby não encontrado.</p>;
    }



    return (
        <div>
            <h1>{detalhes.nome}</h1>

            <p>{detalhes.descricao}</p>

            <p>
                <strong>Categoria:</strong>{" "}
                {detalhes.categoria}
            </p>

            <p>
                <strong>Compatibilidade:</strong>{" "}
                {detalhes.score}
            </p>

            <h2>Por que recomendamos</h2>

            <ul>
                {detalhes.motivos.map((motivo, index) => (
                    <li key={index}>{motivo}</li>
                ))}
            </ul>

            {detalhes.alertas.length > 0 && (
                <>
                    <h2>Pontos de atenção</h2>

                    <ul>
                        {detalhes.alertas.map((alerta, index) => (
                            <li key={index}>{alerta}</li>
                        ))}
                    </ul>
                </>
            )}

            <h2>Sua relação com este hobby</h2>

            <p>
                <strong>Nível:</strong>{" "}
                {detalhes.nivelAtual}
            </p>

            <p>
                <strong>Status:</strong>{" "}
                {detalhes.statusAtual ?? "Ainda não definido"}
            </p>

            <h2>Plano personalizado</h2>

            {erroPlano && (
                <p>{erroPlano}</p>
            )}

            {!detalhes.plano.existe ? (
                <>
                    <p>
                        Você ainda não possui um plano personalizado
                        para este hobby.
                    </p>

                    <button
                        onClick={gerarPlano}
                        disabled={gerandoPlano}
                    >
                        {gerandoPlano
                            ? "Gerando plano..."
                            : "Gerar meu plano"}
                    </button>
                </>
            ) : (
                <>
                    {detalhes.plano.stale && (
                        <div>
                            <p>
                                Seu perfil mudou desde que este plano
                                foi criado.
                            </p>

                            <button
                                onClick={regenerarPlano}
                                disabled={gerandoPlano}
                            >
                                {gerandoPlano
                                    ? "Atualizando plano..."
                                    : "Atualizar plano"}
                            </button>
                        </div>
                    )}

                    <div>
                        {detalhes.plano.conteudo}
                    </div>
                </>
            )}

                    </div>
                );
            }

            export default RecommendationDetails;