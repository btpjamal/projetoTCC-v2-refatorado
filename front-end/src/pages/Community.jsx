import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import axios from "axios";

import "./css/Community.css";
import "./css/RecommendationsLayout.css";


export default function Community() {

    const navigate = useNavigate();

    const [status, setStatus] = useState(null);
    const [usuarios, setUsuarios] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState("");
    const [ativando, setAtivando] = useState(false);

    const nome = localStorage.getItem("nome");
    const token = localStorage.getItem("token");


    useEffect(() => {
        carregarComunidade();
    }, []);


    async function carregarComunidade() {

        try {

            setCarregando(true);
            setErro("");

            const statusResponse = await axios.get(
                "http://localhost:8080/api/v1/social/me/status",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            const statusAtual = statusResponse.data;

            setStatus(statusAtual);


            if (
                statusAtual.elegivel &&
                statusAtual.participa
            ) {

                const usersResponse = await axios.get(
                    "http://localhost:8080/api/v1/community/users",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );

                setUsuarios(usersResponse.data);
            }

        } catch (error) {

            console.error(error);

            setErro(
                error.response?.data?.error ||
                "Não foi possível carregar a comunidade."
            );

        } finally {
            setCarregando(false);
        }
    }


    async function ativarParticipacao() {

        try {

            setAtivando(true);
            setErro("");

            await axios.post(
                "http://localhost:8080/api/v1/social/me/enable",
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            await carregarComunidade();

        } catch (error) {

            console.error(error);

            setErro(
                error.response?.data?.error ||
                "Não foi possível ativar sua participação."
            );

        } finally {
            setAtivando(false);
        }
    }


    async function desativarParticipacao() {

        const confirmar = window.confirm(
            "Deseja sair da comunidade? Seu perfil deixará de aparecer para outros participantes."
        );

        if (!confirmar) {
            return;
        }

        try {

            await axios.post(
                "http://localhost:8080/api/v1/social/me/disable",
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setUsuarios([]);

            setStatus({
                elegivel: true,
                participa: false
            });

        } catch (error) {

            console.error(error);

            setErro(
                error.response?.data?.error ||
                "Não foi possível desativar sua participação."
            );
        }
    }


    function logout() {
        localStorage.clear();
        navigate("/login");
    }


    return (
        <main className="community-page">

            <header className="app-header">

                <div className="app-header-content">

                    <div className="app-brand">
                        <span className="app-brand-icon">✦</span>

                        <div>
                            <span className="app-brand-name">
                                Recommendi.a
                            </span>

                            <span className="app-brand-subtitle">
                                Descubra seu próximo hobby
                            </span>
                        </div>
                    </div>


                    <div className="app-header-actions">

                        <span className="app-user-greeting">
                            Olá, {nome}!
                        </span>

                        <button
                            className="app-header-button"
                            onClick={() =>
                                navigate("/recommendations")
                            }
                        >
                            Recomendações
                        </button>

                        <button
                            className="app-header-button"
                            onClick={() =>
                                navigate("/myProfile")
                            }
                        >
                            Meu Perfil
                        </button>

                        <button
                            className="app-logout-button"
                            onClick={logout}
                        >
                            Sair
                        </button>

                    </div>

                </div>

            </header>


            <section className="community-container">

                {carregando && (
                    <div className="community-state">
                        <div className="community-state-icon">
                            ◌
                        </div>

                        <h2>Carregando comunidade...</h2>

                        <p>
                            Estamos preparando seu Hub Social.
                        </p>
                    </div>
                )}


                {!carregando && erro && (
                    <div className="community-error">
                        {erro}
                    </div>
                )}


                {!carregando &&
                    status &&
                    !status.elegivel && (

                        <div className="community-state">

                            <div className="community-state-icon">
                                ◇
                            </div>

                            <h2>
                                Comunidade indisponível
                            </h2>

                            <p>
                                O Hub Social do Recommendi.a está
                                disponível somente para usuários
                                maiores de 18 anos.
                            </p>

                            <button
                                className="community-secondary-button"
                                onClick={() =>
                                    navigate("/recommendations")
                                }
                            >
                                Voltar às recomendações
                            </button>

                        </div>
                    )}


                {!carregando &&
                    status?.elegivel &&
                    !status.participa && (

                        <div className="community-state">

                            <div className="community-state-icon">
                                ♡
                            </div>

                            <h2>
                                Faça parte da comunidade
                            </h2>

                            <p>
                                Conheça pessoas com hobbies e
                                interesses semelhantes aos seus.
                                Sua participação é opcional e você
                                pode sair quando quiser.
                            </p>

                            <button
                                className="community-primary-button"
                                onClick={ativarParticipacao}
                                disabled={ativando}
                            >
                                {ativando
                                    ? "Entrando..."
                                    : "Participar da comunidade"}
                            </button>

                        </div>
                    )}


                {!carregando &&
                    status?.elegivel &&
                    status.participa && (

                        <>
                            <div className="community-heading">

                                <div>
                                <span className="community-label">
                                    COMUNIDADE
                                </span>

                                    <h1>
                                        Pessoas com interesses parecidos
                                    </h1>

                                    <p>
                                        Descubra participantes que
                                        compartilham hobbies e interesses
                                        com você.
                                    </p>
                                </div>

                                <button
                                    className="community-leave-button"
                                    onClick={desativarParticipacao}
                                >
                                    Sair da comunidade
                                </button>

                            </div>


                            {usuarios.length === 0 ? (

                                <div className="community-state">

                                    <div className="community-state-icon">
                                        ♡
                                    </div>

                                    <h2>
                                        Ainda não há outras pessoas por aqui
                                    </h2>

                                    <p>
                                        Conforme novos participantes entrarem
                                        na comunidade, eles aparecerão aqui.
                                    </p>

                                </div>

                            ) : (

                                <div className="community-grid">

                                    {usuarios.map(usuario => (

                                        <SocialUserCard
                                            key={usuario.userId}
                                            usuario={usuario}
                                        />

                                    ))}

                                </div>
                            )}

                        </>
                    )}

            </section>

        </main>
    );
}


function SocialUserCard({ usuario }) {

    const afinidade = usuario.afinidade;

    const possuiAfinidade =
        afinidade &&
        (
            afinidade.praticandoEmComum?.length > 0 ||
            afinidade.hobbiesDeInteresseEmComum?.length > 0 ||
            afinidade.interessesEmComum?.length > 0
        );


    return (
        <article className="social-user-card">

            <div className="social-user-header">

                <div className="social-user-avatar">
                    {usuario.nome
                        ?.charAt(0)
                        .toUpperCase()}
                </div>

                <div>
                    <h2>{usuario.nome}</h2>

                    {possuiAfinidade && (
                        <span className="social-affinity-badge">
                            Interesses em comum
                        </span>
                    )}
                </div>

            </div>


            <p className="social-user-summary">
                {usuario.resumo}
            </p>


            {afinidade?.praticandoEmComum?.length > 0 && (

                <AffinityGroup
                    titulo="Vocês praticam"
                    itens={afinidade.praticandoEmComum}
                />
            )}


            {afinidade?.hobbiesDeInteresseEmComum?.length > 0 && (

                <AffinityGroup
                    titulo="Hobbies de interesse em comum"
                    itens={afinidade.hobbiesDeInteresseEmComum}
                />
            )}


            {afinidade?.interessesEmComum?.length > 0 && (

                <AffinityGroup
                    titulo="Interesses em comum"
                    itens={afinidade.interessesEmComum}
                />
            )}


            {!possuiAfinidade && (
                <p className="social-no-affinity">
                    Vocês ainda não possuem interesses em comum,
                    mas talvez descubram novos hobbies juntos.
                </p>
            )}

        </article>
    );
}


function AffinityGroup({ titulo, itens }) {

    return (
        <div className="social-affinity-group">

            <span>{titulo}</span>

            <div className="social-affinity-tags">

                {itens.map(item => (
                    <span key={item}>
                        {item}
                    </span>
                ))}

            </div>

        </div>
    );
}