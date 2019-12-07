using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.OpenIdConnect;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Protocols.OpenIdConnect;

namespace Connect22 {
    public class Startup {
        ILogger<Startup> _logger;
        public Startup(IConfiguration configuration, ILogger<Startup> logger) {
            Configuration = configuration;
            _logger = logger;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services) {
            services.AddAuthentication(options => {
                options.DefaultScheme = "Cookies";
                options.DefaultChallengeScheme = "oidc";
            })
                .AddCookie("Cookies")
                .AddOpenIdConnect("oidc", options => {
                    // http://localhost:5000/api/hello/hello
                    // "http://localhost:8080/auth/realms/master/protocol/openid-connect/auth";
                    // "http://localhost:8080/auth/realms/master/.well-known/openid-configuration/
                    options.Authority = "http://localhost:8080/auth/realms/master";
                    options.RequireHttpsMetadata = false;
                    options.ClientId = "hello";
                    options.ClientSecret = "830c9965-2990-4c22-8adc-6af4343b9040";
                    options.ResponseType = OpenIdConnectResponseType.CodeIdTokenToken;
                    options.SignedOutCallbackPath = "/signin-oidc";
                    //options.ClaimActions.MapJsonKey(ClaimTypes.NameIdentifier, "id");

                    void Show(HttpRequest r) {
                        if (r.HasFormContentType) {
                            foreach (var item in r.Form) {
                                _logger.LogInformation("{0} - {1}", item.Key, item.Value);
                            }
                        }
                    }

                    options.Events = new OpenIdConnectEvents {
                        OnRedirectToIdentityProvider = async x => {
                            _logger.LogInformation("-- OnRedirectToIdentityProvider --");
                            foreach (var item in x.Request.Query) {
                                _logger.LogInformation("{0} - {1}", item.Key, item.Value);
                            }
                            Show(x.Request);
                        },

                        OnMessageReceived = async x => {
                            _logger.LogInformation("-- OnMessageReceived --");
                            // Show(x.Request);
                            await Task.CompletedTask;
                        },
                        OnTicketReceived = async x => {
                            _logger.LogInformation("-- OnTicketReceived --");
                            string iPlanetProCookie = string.Empty;

                            // String jwtTokenString = x.Request.Form["id_token"];
                            // _logger.LogInformation(jwtTokenString);
                            // Show(x.Request);

                            await Task.CompletedTask;
                        },
                        OnUserInformationReceived = async x => {
                            _logger.LogInformation("-- OnUserInformationReceived --");
                            // Show(x.Request);
                            await Task.CompletedTask;

                        },
                        OnAuthorizationCodeReceived = async x => {
                            _logger.LogInformation("-- OnAuthorizationCodeReceived --");
                            // Show(x.Request);
                            await Task.CompletedTask;
                        }
                    };
                });

            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_2);
        }

        public void Configure(IApplicationBuilder app, IHostingEnvironment env) {
            if (env.IsDevelopment()) {
                app.UseDeveloperExceptionPage();
            } else {
                app.UseHsts();
            }

            //app.UseHttpsRedirection();
            app.UseAuthentication();
            app.UseMvc();
        }
    }
}
