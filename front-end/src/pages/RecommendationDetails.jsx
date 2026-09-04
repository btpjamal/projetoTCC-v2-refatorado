import { useEffect, useState } from "react";
import {api} from "../api/api.js";
import ReactMarkdown from "react-markdown";
import "../pages/css/RecommendationsDetails.css";
import { useNavigate, useParams } from "react-router-dom";

function RecommendationDetails() {
    const { hobbyId } = useParams();

    const [detalhes, setDetalhes] = useState(null);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState("");
    const [gerandoPlano, setGerandoPlano] = useState(false);
    const [erroPlano, setErroPlano] = useState("");
    const navigate = useNavigate();


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
        <div className="recommendation-details-page">

            <button
                className="back-button"
                onClick={() => navigate(-1)}
            >
                ← Voltar
            </button>

            <header className="recommendation-details-header">
                <h1>{detalhes.nome}</h1>

                <p className="recommendation-details-description">
                    {detalhes.descricao}
                </p>

                <div className="recommendation-details-meta">
                <span className="recommendation-details-badge">
                    <strong>Categoria:</strong>{" "}
                    {detalhes.categoria}
                </span>

                    <span className="recommendation-details-badge score-badge">
                    <strong>Compatibilidade:</strong>{" "}
                        {detalhes.score}
                </span>
                </div>
            </header>

            <section className="recommendation-details-section">
                <h2>Por que recomendamos</h2>

                <ul>
                    {detalhes.motivos.map((motivo, index) => (
                        <li key={index}>{motivo}</li>
                    ))}
                </ul>
            </section>

            {detalhes.alertas.length > 0 && (
                <section className="recommendation-details-section">
                    <h2>Pontos de atenção</h2>

                    <ul>
                        {detalhes.alertas.map((alerta, index) => (
                            <li key={index}>{alerta}</li>
                        ))}
                    </ul>
                </section>
            )}

            <section className="recommendation-details-section">
                <h2>Sua relação com este hobby</h2>

                <div className="recommendation-relation">
                    <span className="relation-badge">
                        <strong>Nível:</strong>{" "}
                        {detalhes.nivelAtual}
                    </span>

                    <span className="relation-badge">
                        <strong>Status:</strong>{" "}
                        {detalhes.statusAtual ?? "Ainda não definido"}
                    </span>
                </div>
            </section>

            <section className="recommendation-details-section">
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
                            className="plan-action-button"
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
                            <div className="plan-stale-warning">
                                <p>
                                    Seu perfil mudou desde que este plano
                                    foi criado.
                                </p>

                                <button
                                    className="plan-action-button"
                                    onClick={regenerarPlano}
                                    disabled={gerandoPlano}
                                >
                                    {gerandoPlano
                                        ? "Atualizando plano..."
                                        : "Atualizar plano"}
                                </button>
                            </div>
                        )}

                        <div className="personalized-plan-content">
                            <ReactMarkdown>
                                {detalhes.plano.conteudo}
                            </ReactMarkdown>
                        </div>
                    </>
                )}
            </section>

        </div>
    );
    }
    export default RecommendationDetails;